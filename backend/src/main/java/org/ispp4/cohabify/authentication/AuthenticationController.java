package org.ispp4.cohabify.authentication;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.JwtTokenDto;
import org.ispp4.cohabify.dto.LoginRequest;
import org.ispp4.cohabify.dto.UserRegisterRequest;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

	private UserService userService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request, BindingResult result) throws BadRequestException {
		
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
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone().replaceAll("-", ""));
		user.setAuthorities(List.of("User"));
		user = userService.save(user);
		// TODO: Add the user full name when it is fixed in the model
		
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
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, 
			"Authentication=" + jwt + "; Max-Age=604800; Path=/; Secure; HttpOnly");
		
		return ResponseEntity.status(HttpStatus.OK)
							 .headers(headers)
							 .body(JwtTokenDto.builder().user(user).token(jwt).build());
	}
	
}
