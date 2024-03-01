package org.ispp4.cohabify.dto;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormItemValidationError {
	
	private String field;
	private String message;
	private Object rejectedValue;
	private String code;
	
	public FormItemValidationError(FieldError fieldError) {
		this.field = fieldError.getField();
		this.message = fieldError.getDefaultMessage();
		this.rejectedValue = fieldError.getRejectedValue();
		this.code = fieldError.getCode();
	}
	
}
