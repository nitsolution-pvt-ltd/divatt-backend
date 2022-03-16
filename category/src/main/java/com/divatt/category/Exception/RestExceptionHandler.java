package com.divatt.category.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.divatt.category.Exception.CustomErrorMessage;
import com.divatt.category.Exception.CustomException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<CustomErrorMessage> handleException(CustomException exc){
		
		CustomErrorMessage customeErrorMessage = new CustomErrorMessage(HttpStatus.BAD_REQUEST.value()
				, exc.getMessage(),System.currentTimeMillis());
		return new ResponseEntity<>(customeErrorMessage, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomErrorMessage> handleException(Exception exc){
		
		CustomErrorMessage customeErrorMessage = new CustomErrorMessage(HttpStatus.BAD_REQUEST.value(),
				exc.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(customeErrorMessage, HttpStatus.BAD_REQUEST);
		
	}
}