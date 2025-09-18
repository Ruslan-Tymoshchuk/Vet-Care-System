package com.system.vetcare.controller;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.system.vetcare.exception.ExceptionHandlerResponse;
import io.jsonwebtoken.JwtException;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvisor {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleExceptionUsernameNotFound(UsernameNotFoundException exception) {
        return ResponseEntity
			     .status(UNAUTHORIZED)
				 .body(buildExceptionResponse(NOT_FOUND, exception.getMessage())); 
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleExceptionResourceNotFound(NoSuchElementException exception) {
        return ResponseEntity
			     .status(UNAUTHORIZED)
				 .body(buildExceptionResponse(NOT_FOUND, exception.getMessage())); 
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleExceptionUsernameBadCredentials(BadCredentialsException exception) {
        return ResponseEntity
			     .status(UNAUTHORIZED)
				 .body(buildExceptionResponse(UNAUTHORIZED, exception.getMessage())); 
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleExceptionUsernameLocked(LockedException exception) {
        return ResponseEntity
			     .status(UNAUTHORIZED)
				 .body(buildExceptionResponse(LOCKED, exception.getMessage())); 
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {
    	return ResponseEntity
			     .status(UNAUTHORIZED)
				 .body(buildExceptionResponse(NOT_ACCEPTABLE, exception.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleJwtException(JwtException exception) {
    	return ResponseEntity
			     .status(UNAUTHORIZED)
				 .body(buildExceptionResponse(UNAUTHORIZED, exception.getMessage()));
    }
    
	private ExceptionHandlerResponse buildExceptionResponse(HttpStatus status, String message) {
	     return new ExceptionHandlerResponse(
	                  now(),
	                  status.value(),
	                  status,
	                  message);
	    }
	
}