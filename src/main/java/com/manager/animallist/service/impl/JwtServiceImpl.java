package com.manager.animallist.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.manager.animallist.service.JwtService;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static io.jsonwebtoken.security.Keys.*;

@Component
public class JwtServiceImpl implements JwtService {

    private static final Key secretKey = secretKeyFor(SignatureAlgorithm.HS256);
    
    @Value("${jwt.cookies.valid.time}")
    private Integer jwtCookiesValidTime;
    @Value("${jwt.token.valid.time}")
    private Integer jwtTokenValidTime;
    private final List<String> tokenBlackList = new ArrayList<>();

    @Override
    public String extractUserEmail(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidTime)).signWith(secretKey)
                .compact();
    }

    @Override
    public boolean validate(String token, UserDetails userDetails) {
        String convertedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
        final String username = extractUserEmail(convertedToken);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(convertedToken);
    }

    @Override
    public ResponseCookie createJwtCookie(String token) {
        return ResponseCookie.from(AUTHORIZATION, URLEncoder.encode("Bearer " + token, StandardCharsets.UTF_8))
                .maxAge(jwtCookiesValidTime).httpOnly(true).build();
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
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }
    
    private Date getExpirationDate(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }
}
