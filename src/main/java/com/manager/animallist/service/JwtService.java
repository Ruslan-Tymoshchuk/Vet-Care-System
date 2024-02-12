package com.manager.animallist.service;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserEmail(String token);
    
    String generateToken(String userEmail);
    
    boolean validate(String token, UserDetails userDetails);
    
    ResponseCookie createJwtCookie(String token);
    
    boolean isTokenInBlackList(String token);
    
    void addTokenToBlacklist(String token);

}