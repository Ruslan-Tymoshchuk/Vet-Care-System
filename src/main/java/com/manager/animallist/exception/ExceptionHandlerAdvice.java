package com.manager.animallist.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

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

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleExceptionResourceNotFound(NoSuchElementException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception);
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
}