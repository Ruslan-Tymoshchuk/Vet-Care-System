package com.manager.animallist.service;

import com.manager.animallist.payload.UserEmailValidationRequest;
import com.manager.animallist.payload.UserEmailValidationResponse;

public interface UsernameValidator {

    UserEmailValidationResponse usernameIsAlreadyTaken(UserEmailValidationRequest userEmailValidationRequest);

}