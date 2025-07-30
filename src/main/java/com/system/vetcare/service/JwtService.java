package com.system.vetcare.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    
    String generateToken(String userEmail, Integer validTime);
    
    boolean isTokenBlacklisted(String token);
      
    void addTokenToBlacklist(String token);

    Claims parseToken(String token);

}