package com.system.vetcare.service;

import com.system.vetcare.domain.User;
import com.system.vetcare.payload.request.AuthenticationRequest;
import com.system.vetcare.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    
    User resolvePrincipal(AuthenticationRequest credential);
    
    AuthenticationResponse buildAuthenticationResponse(User user);
    
    void revokePrincipalAuthentication();

}