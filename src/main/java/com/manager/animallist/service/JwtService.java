package com.manager.animallist.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserEmail(String token);
    
    String generateToken(String userEmail, Integer validTime);
    
    boolean validate(String token, UserDetails userDetails);
    
    boolean isTokenBlacklisted(String token);
    
    void addTokenToBlacklist(String token);

}