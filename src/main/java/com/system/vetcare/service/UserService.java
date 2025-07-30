package com.system.vetcare.service;

import com.system.vetcare.domain.Pet;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.AuthenticationResponse;
import com.system.vetcare.payload.RegistrationRequest;

public interface UserService {
    
    User findByEmail(String email);
    
    AuthenticationResponse saveNewUser(RegistrationRequest registerRequest);

    void assignUser(Pet animal, String userEmail);
    
}