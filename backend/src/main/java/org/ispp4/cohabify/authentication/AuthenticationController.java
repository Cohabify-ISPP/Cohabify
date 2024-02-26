package org.ispp4.cohabify.authentication;

import org.ispp4.cohabify.dto.LoginRequest;
import org.ispp4.cohabify.dto.UserDto;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final UserService userService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
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
							 .body(UserDto.builder().user(user).token(jwt).build());
	}
	
}
