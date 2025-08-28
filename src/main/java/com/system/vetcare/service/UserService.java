package com.system.vetcare.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.request.RegistrationRequest;

public interface UserService extends UserDetailsService {
    
    User save(RegistrationRequest registerRequest);

    void updateLoginTimestamp(User user);
    
}