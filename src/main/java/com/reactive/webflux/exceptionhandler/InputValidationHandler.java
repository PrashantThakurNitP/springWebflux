package com.reactive.webflux.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.reactive.webflux.dto.InputFailedValidationResponse;
import com.reactive.webflux.exception.InputValidationException;

@ControllerAdvice
public class InputValidationHandler {
	
	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<InputFailedValidationResponse>  handleException(InputValidationException ex) {
		InputFailedValidationResponse response = new InputFailedValidationResponse();
		response.setErrorCode(ex.getErrorcode());
		response.setInput(ex.getInput());
		response.setMessage(ex.getMessage());
		return ResponseEntity.badRequest().body(response);
	}

}
