package com.manager.animallist.service;

import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;

public interface UserService {
    
    AuthenticationResponse saveNewUser(RegistrationRequest registerRequest);
    
}