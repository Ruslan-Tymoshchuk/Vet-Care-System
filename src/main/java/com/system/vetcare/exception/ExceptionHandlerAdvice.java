package com.system.vetcare.exception;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.JwtException;
import java.util.NoSuchElementException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionHandlerResponse handleExceptionUsernameNotFound(UsernameNotFoundException exception) {
        return new ExceptionHandlerResponse(now(), NOT_FOUND.value(), NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionHandlerResponse handleExceptionResourceNotFound(NoSuchElementException exception) {
        return new ExceptionHandlerResponse(now(), NOT_FOUND.value(), NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionHandlerResponse handleExceptionUsernameBadCredentials(BadCredentialsException exception) {
        return new ExceptionHandlerResponse(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(LOCKED)
    public ExceptionHandlerResponse handleExceptionUsernameLocked(LockedException exception) {
        return new ExceptionHandlerResponse(now(), LOCKED.value(), LOCKED, exception.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ExceptionHandlerResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {
        return new ExceptionHandlerResponse(now(), NOT_ACCEPTABLE.value(), NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionHandlerResponse handleJwtException(JwtException exception) {
        return new ExceptionHandlerResponse(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage());
    }
}