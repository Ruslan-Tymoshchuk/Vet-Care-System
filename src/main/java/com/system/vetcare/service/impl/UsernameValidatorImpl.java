package com.system.vetcare.service.impl;

import org.springframework.stereotype.Service;

import com.system.vetcare.payload.UserEmailValidationRequest;
import com.system.vetcare.payload.UserEmailValidationResponse;
import com.system.vetcare.repository.UserRepository;
import com.system.vetcare.service.UsernameValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsernameValidatorImpl implements UsernameValidator {

    public static final String EMAIL_IS_ALREADY_TAKEN = "This email is already taken";
    public static final String EMAIL_IS_ALLOWED = "This email is allowed";
    
    private final UserRepository userRepository;
    
    public UserEmailValidationResponse usernameIsAlreadyTaken(UserEmailValidationRequest userEmailValidationRequest) {
      if (userRepository.existsByEmail(userEmailValidationRequest.getEmail())) {
          return UserEmailValidationResponse
                                    .builder()
                                    .response(EMAIL_IS_ALREADY_TAKEN)
                                    .build();
      } else {
          return UserEmailValidationResponse
                  .builder()
                  .response(EMAIL_IS_ALLOWED)
                  .build();
      }
    }
}