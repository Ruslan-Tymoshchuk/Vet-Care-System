package com.rtim.zoo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleExceptionUsernameNotFound(UsernameNotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleExceptionResourceNotFound(ResourceNotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(UsernameAlredyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleExceptionUsernameAlredyExists(UsernameAlredyExistsException exception) {
        return new ApiError(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleExceptionUsernameBadCredentials(BadCredentialsException exception) {
        return new ApiError(HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ApiError handleExceptionUsernameLocked(LockedException exception) {
        return new ApiError(HttpStatus.LOCKED, exception);
    }

    @ExceptionHandler(AnimalNickNameAlredyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleExceptionAnimalNickNameAlredyExists(AnimalNickNameAlredyExistsException exception) {
        return new ApiError(HttpStatus.CONFLICT, exception);
    }
}