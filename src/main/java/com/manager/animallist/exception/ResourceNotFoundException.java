package com.manager.animallist.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Integer id) {
        super(message + id);
      }
}