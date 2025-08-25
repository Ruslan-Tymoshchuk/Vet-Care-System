package com.system.vetcare.service.impl;

import static com.system.vetcare.payload.JwtMarkers.ACCESS_TOKEN;
import static com.system.vetcare.payload.JwtMarkers.BEARER_TOKEN_TYPE;
import static com.system.vetcare.payload.JwtMarkers.REFRESH_TOKEN;
import static java.lang.String.format;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import com.system.vetcare.domain.User;
import com.system.vetcare.service.CookiesService;
import com.system.vetcare.service.JwtService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookiesServiceImpl implements CookiesService {
   
    public static final String JWT_FORMAT = "%s %s";
    public static final String EMPTY_DELIMITER = "";
    public static final String ABSOLUTE_PATH = "/";
    public static final String LIMIT_THE_SCOPE = "None";
    
    @Value("${access.token.life.time}")
    private Integer accessTokenLifeTime;
    @Value("${jwt.cookie.life.time}")
    private Integer jwtCookieLifeTime;
    @Value("${refresh.token.life.time}")
    private Integer refreshTokenLifeTime;
    private final JwtService jwtService;
    
    @Override
    public String buildCookie(String cookieName, String cookieValue, Integer cookieLifeTime) {  
        return ResponseCookie.from(cookieName, encode(cookieValue, UTF_8))
                .httpOnly(true)
                .secure(true)
                .sameSite(LIMIT_THE_SCOPE)
                .path(ABSOLUTE_PATH)
                .maxAge(cookieLifeTime)
                .build()
                .toString();
    }  

    @Override
    public HttpHeaders issueJwtCookies(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SET_COOKIE, buildCookie(ACCESS_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE, jwtService.generateToken(user.getEmail(), accessTokenLifeTime)),
                jwtCookieLifeTime));
        headers.add(SET_COOKIE, buildCookie(REFRESH_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE,
                        jwtService.generateToken(user.getEmail(), refreshTokenLifeTime)),
                        jwtCookieLifeTime));
        return headers;
    }
    
    @Override
    public HttpHeaders revokeJwtCookies(Cookie[] cookies) {
        HttpHeaders headers = new HttpHeaders();
        stream(cookies).forEach(cookie -> {
            if (cookie.getName().equals(ACCESS_TOKEN) || cookie.getName().equals(REFRESH_TOKEN)
                    && startsWithIgnoreCase(cookie.getValue(), BEARER_TOKEN_TYPE)) {
                jwtService.addTokenToBlacklist(cookie.getValue().substring(7));
                headers.add(SET_COOKIE, clearCookie(cookie));
            }
        });
        return headers;
    }
    
    private String clearCookie(Cookie cookie) {   
        return buildCookie(cookie.getName(), EMPTY_DELIMITER, 0);
    }
 
}