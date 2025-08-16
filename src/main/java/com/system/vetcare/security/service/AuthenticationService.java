package com.system.vetcare.security.service;

import javax.servlet.http.HttpServletRequest;
import com.system.vetcare.domain.User;
import com.system.vetcare.security.payload.AuthenticationRequest;
import com.system.vetcare.security.payload.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    
    AuthenticationResponse buildAuthenticationResponse(User user);
    
    void logout(HttpServletRequest request); 

}