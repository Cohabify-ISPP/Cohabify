package org.ispp4.cohabify.authentication;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.ispp4.cohabify.configuration.CustomAuthenticationManager;
import org.ispp4.cohabify.dto.ErrorResponse;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.JwtTokenDto;
import org.ispp4.cohabify.dto.LoginRequest;
import org.ispp4.cohabify.dto.UserRegisterRequest;
import org.ispp4.cohabify.storage.StorageService;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.utils.MailHelper;
import org.ispp4.cohabify.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

	private UserService userService;
	private JwtService jwtService;
	private CustomAuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private StorageService storageService;
	private RandomStringGenerator randomStringGenerator;
	private MailHelper mailHelper;

	@Value("${google.public.keys:default}")
	private String[] googlePublicKeys;
	
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> register(@Valid @RequestPart("string-data") UserRegisterRequest request, BindingResult result,  
									  @RequestPart("profile-pic") MultipartFile image) throws BadRequestException {
		if(result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(result.getFieldErrors()
											.stream()
											.map(fe -> new FormItemValidationError(fe))
											.toList());
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setName(request.getName());
		if (request.getGoogleOAuthToken() == null) {
			user.setEnabled(false);
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		} else {
			user.setEnabled(true);
			String pass = "";
			new SecureRandom().ints(100).forEach(x->pass.concat((x + "")));
			user.setPassword(passwordEncoder.encode(pass));
		}
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone().replaceAll("-", ""));
		user.setTag(request.getTag());
		user.setGender(request.getGender());
		user.setAuthorities(List.of("User"));
		user.setPlan(Plan.BASIC);
		user.setGoogleOAuthToken(request.getGoogleOAuthToken());
		user.setDescription("¡Hola, estoy utilizando Cohabify!");
		try{
			user = userService.save(user);
      user.setVerificationCode(randomStringGenerator.extendStringWithRandomCharactersUrlSafe(user.getId().toString(), 64));
		} catch (IllegalStateException e) {
			if (e.getMessage().contains("nombre de usuario")) {
				FormItemValidationError error = new FormItemValidationError();
				error.setField("username");
				error.setCode("AlreadyExists");
				error.setMessage("Ya existe un usuario registrado con el nombre de usuario "+user.getUsername()+". Por favor, elija otro.");
				error.setRejectedValue(request.getUsername());
				return ResponseEntity.status(HttpStatus.CONFLICT)
						 .body(error);
			}else if (e.getMessage().contains("email")) {
				FormItemValidationError error = new FormItemValidationError();
				error.setField("email");
				error.setCode("AlreadyExists");
				error.setMessage("Ya existe un usuario registrado con el email "+user.getEmail()+". Por favor, elija otro.");
				error.setRejectedValue(request.getEmail());
				return ResponseEntity.status(HttpStatus.CONFLICT)
						 .body(error);
			}else if (e.getMessage().contains("teléfono")) {
				FormItemValidationError error = new FormItemValidationError();
				error.setField("phone");
				error.setCode("AlreadyExists");
				error.setMessage("Ya existe un usuario registrado con el teléfono "+user.getPhone()+". Por favor, elija otro.");
				error.setRejectedValue(request.getPhone());
				return ResponseEntity.status(HttpStatus.CONFLICT)
						 .body(error);
			}
		}catch (Exception e) {
			e.printStackTrace();	
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
					 .body(e.getMessage());
		}
		
		// Save the image and add the static uri to the user
		String[] filename_split = image.getOriginalFilename().split("\\.");
		String filename = user.getJsonId() + "." + filename_split[filename_split.length-1];
		String static_path;
		try {
			static_path = storageService.saveImage(filename, image);
		} catch (IOException e) {
			userService.deleteById(user.getId());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body(e.getMessage());
		}
		
		user.setImageUri(static_path);
		user = userService.save(user);

		if(!user.getEnabled()) {
			Boolean mailResult = mailHelper.sendVerificationEmail(user);
			if(!mailResult) {
				userService.deleteById(user.getId());
				return ResponseEntity.status(500)
									 .body(new ErrorResponse("Algo ha fallado enviando el email de verificación."));
			}
		}
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDto> login(@RequestBody LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		User user = userService.getUserByUsername(request.getUsername());
		if (user == null)
			throw new BadCredentialsException("Invalid username or password.");
		
		if (!userService.isUserPlanValid(user)) {
			user.setPlan(Plan.BASIC);
			userService.save(user);
		}
		String jwt = jwtService.generateToken(user);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(JwtTokenDto.builder().user(user).token(jwt).build());
	}

	@GetMapping("/register/verify/{verificationCode}")
	public ResponseEntity<?> verifyAccount(@PathVariable("verificationCode") String verificationCode) {
		User user = userService.findByVerificationCode(verificationCode);

		if(user == null) {
			return ResponseEntity.status(404).build();
		}

		user.setEnabled(true);
		userService.save(user);

		return ResponseEntity.status(200).build();
	}
	
	@PostMapping("/login/google")
	public ResponseEntity<?> loginGoogle(@RequestBody String request) {
		UserDetails userDetails;
		try {
			userDetails = (UserDetails) authenticationManager.authenticate(new GoogleAuthenticationToken(request, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), googlePublicKeys)).getPrincipal();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		User user = userService.getUserByUsername(userDetails.getUsername());
		if (user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		String jwt = jwtService.generateToken(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(JwtTokenDto.builder().user(user).token(jwt).build());
	}
	
	@PostMapping("/getUser")
	public ResponseEntity<User> getUser(@RequestHeader("Authentication") String jwt) {
		if (jwt == null || jwt.isEmpty()){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(null);
		}
		jwt = jwt.replace("Bearer ", "").trim();
		String username = jwtService.extractUserName(jwt);
		User user = userService.getUserByUsername(username);
		return ResponseEntity.status(HttpStatus.OK)
							 .body(user);
	}

}
