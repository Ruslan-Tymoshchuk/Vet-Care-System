package com.system.vetcare.service;

import com.system.vetcare.payload.request.UserEmailValidationRequest;
import com.system.vetcare.payload.response.UserEmailValidationResponse;

public interface UsernameValidator {

    UserEmailValidationResponse usernameIsAlreadyTaken(UserEmailValidationRequest userEmailValidationRequest);

}