package com.manager.animallist.exception;

@SuppressWarnings("serial")
public class UsernameAlredyExistsException extends RuntimeException {

    public UsernameAlredyExistsException(String message) {
        super(message);
      }
}