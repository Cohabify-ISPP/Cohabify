package org.ispp4.cohabify.authentication;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.ispp4.cohabify.configuration.CustomAuthenticationManager;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.JwtTokenDto;
import org.ispp4.cohabify.dto.LoginRequest;
import org.ispp4.cohabify.dto.UserRegisterRequest;
import org.ispp4.cohabify.storage.StorageService;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private UserService userService;
	private JwtService jwtService;
	private CustomAuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private StorageService storageService;

	public AuthenticationController(UserService userService, JwtService jwtService, CustomAuthenticationManager authenticationManager,
									PasswordEncoder passwordEncoder, StorageService storageService) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.storageService = storageService;
	}

	@Value("${google.public.key}")
	private String googlePublicKey;
	
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
		
		User user = userService.getUserByUsername(request.getUsername());
		if(user != null) {
			FormItemValidationError error = new FormItemValidationError();
			error.setField("username");
			error.setCode("AlreadyExists");
			error.setMessage("An user with this username already exists.");
			error.setRejectedValue(request.getUsername());
			return ResponseEntity.status(HttpStatus.CONFLICT)
					 .body(error);
		}
		
		user = new User();
		user.setUsername(request.getUsername());
		user.setName(request.getName());
		if (request.getGoogleOAuthToken() != null) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone().replaceAll("-", ""));
		user.setTag(request.getTag());
		user.setGender(request.getGender());
		user.setAuthorities(List.of("User"));
		user.setPlan(Plan.BASIC);
		user.setGoogleOAuthToken(request.getGoogleOAuthToken());
		user = userService.save(user);
		
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
		
		String jwt = jwtService.generateToken(user);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(JwtTokenDto.builder().user(user).token(jwt).build());
	}

	@PostMapping("/login/google")
	public ResponseEntity<?> loginGoogle(@RequestBody String request) {
		UserDetails userDetails;
		try {
			userDetails = (UserDetails) authenticationManager.authenticate(new GoogleAuthenticationToken(request, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")),googlePublicKey)).getPrincipal();
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
