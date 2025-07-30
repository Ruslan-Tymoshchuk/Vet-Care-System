package com.manager.animallist.service;

import com.manager.animallist.domain.Pet;
import com.manager.animallist.domain.User;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;

public interface UserService {
    
    User findByEmail(String email);
    
    AuthenticationResponse saveNewUser(RegistrationRequest registerRequest);

    void assignUser(Pet animal, String userEmail);
    
}