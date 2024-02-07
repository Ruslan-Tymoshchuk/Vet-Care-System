package com.manager.animallist.service;

import javax.servlet.http.HttpServletRequest;
import com.manager.animallist.payload.AuthenticationRequest;
import com.manager.animallist.payload.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    
    void logout(HttpServletRequest request);

}
