package com.system.vetcare.service;

import com.system.vetcare.domain.Pet;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.RegistrationRequest;
import com.system.vetcare.security.payload.AuthenticationResponse;

public interface UserService {
    
    User findByEmail(String email);
    
    AuthenticationResponse saveNewUser(RegistrationRequest registerRequest);

    void assignUser(Pet animal, String userEmail);

    User updateLoginTimestamp(User authenticatedUser);
    
}