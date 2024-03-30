package com.manager.animallist.service.impl;

import static java.lang.System.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import com.manager.animallist.service.JwtService;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import static io.jsonwebtoken.security.Keys.*;

@Component
public class JwtServiceImpl implements JwtService {

    public static final Key SECRET_KEY = secretKeyFor(SignatureAlgorithm.HS256);
    
    private final Set<String> tokenBlackList = new HashSet<>();

    @Override
    public String generateToken(String userEmail, Integer validTime) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(currentTimeMillis()))
                .setExpiration(new Date(currentTimeMillis() + validTime))
                .signWith(SECRET_KEY)
                .compact();
    }
        
    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenBlackList.contains(token);
    }
    
    @Override
    public void addTokenToBlacklist(String token) {
        tokenBlackList.add(token);
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}