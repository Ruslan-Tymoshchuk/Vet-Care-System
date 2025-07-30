package com.system.vetcare.service;

import com.system.vetcare.payload.UserEmailValidationRequest;
import com.system.vetcare.payload.UserEmailValidationResponse;

public interface UsernameValidator {

    UserEmailValidationResponse usernameIsAlreadyTaken(UserEmailValidationRequest userEmailValidationRequest);

}