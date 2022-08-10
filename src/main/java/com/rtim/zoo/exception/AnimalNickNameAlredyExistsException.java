package com.rtim.zoo.exception;

@SuppressWarnings("serial")
public class AnimalNickNameAlredyExistsException extends RuntimeException {

    public AnimalNickNameAlredyExistsException(String message) {
        super(message);
      }
}