package com.system.vetcare.service;

import javax.servlet.http.HttpServletRequest;

import com.system.vetcare.payload.AuthenticationRequest;
import com.system.vetcare.payload.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    
    void logout(HttpServletRequest request);

}
