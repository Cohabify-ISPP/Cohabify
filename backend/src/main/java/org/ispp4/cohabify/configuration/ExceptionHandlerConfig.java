package org.ispp4.cohabify.configuration;

import org.ispp4.cohabify.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
public class ExceptionHandlerConfig {

	@ExceptionHandler(value = { AuthenticationException.class, SignatureException.class })
	@ResponseBody
	public <T extends Exception> ResponseEntity<ErrorResponse> handleException(T ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().message(ex.getMessage()).build());
	}
	
}
