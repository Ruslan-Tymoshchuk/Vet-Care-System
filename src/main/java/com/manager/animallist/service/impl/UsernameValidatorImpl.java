package com.manager.animallist.service.impl;

import org.springframework.stereotype.Service;
import com.manager.animallist.payload.UserEmailValidationRequest;
import com.manager.animallist.payload.UserEmailValidationResponse;
import com.manager.animallist.repository.UserRepository;
import com.manager.animallist.service.UsernameValidator;

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