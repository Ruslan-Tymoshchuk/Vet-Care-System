package com.manager.animallist.service.impl;

import static java.lang.System.*;
import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.manager.animallist.service.JwtService;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static io.jsonwebtoken.security.Keys.*;

@Component
public class JwtServiceImpl implements JwtService {

    public static final Key SECRET_KEY = secretKeyFor(SignatureAlgorithm.HS256);
    
    private final List<String> tokenBlackList = new ArrayList<>();

    @Override
    public String extractUserEmail(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

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
    public boolean validate(String token, UserDetails userDetails) {
        String convertedToken = decode(token, UTF_8);
        final String username = extractUserEmail(convertedToken);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(convertedToken);
    }

    @Override
    public boolean isTokenInBlackList(String token) {
        return tokenBlackList.contains(token);
    }
    
    @Override
    public void addTokenToBlacklist(String token) {
        tokenBlackList.add(token);
    }
    
    private <T> T getClaim(String token, Function<Claims, T> clamsResolver) {
        final Claims claims = getAllClaims(token);
        return clamsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }
    
    private Date getExpirationDate(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }
}
