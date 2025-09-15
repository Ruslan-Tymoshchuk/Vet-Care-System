package com.system.vetcare.service;

import com.system.vetcare.domain.User;
import com.system.vetcare.security.payload.AuthenticationRequest;
import com.system.vetcare.security.payload.AuthenticationResponse;

public interface AuthenticationService {
    
    User resolvePrincipal(AuthenticationRequest credential);
    
    AuthenticationResponse buildAuthenticationResponse(User user);
    
    void revokePrincipalAuthentication();

}