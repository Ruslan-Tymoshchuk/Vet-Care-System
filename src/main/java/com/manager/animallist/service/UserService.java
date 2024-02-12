package com.manager.animallist.service;

import com.manager.animallist.domain.Animal;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;

public interface UserService {
    
    AuthenticationResponse saveNewUser(RegistrationRequest registerRequest);

    void assignUser(Animal animal, String userEmail);
    
}