package com.system.vetcare.service.impl;

import static com.system.vetcare.payload.JwtMarkers.ACCESS_TOKEN;
import static com.system.vetcare.payload.JwtMarkers.BEARER_TOKEN_TYPE;
import static com.system.vetcare.payload.JwtMarkers.REFRESH_TOKEN;
import static java.lang.String.format;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static java.util.Collections.emptyMap;
import static java.util.Objects.nonNull;
import java.util.Map;
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
    public String buildCookie(String name, String value, Integer lifeTime) {  
        return ResponseCookie.from(name, encode(value, UTF_8))
                .httpOnly(true)
                .secure(true)
                .sameSite(LIMIT_THE_SCOPE)
                .path(ABSOLUTE_PATH)
                .maxAge(lifeTime)
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
        extractJwtTokens(cookies).forEach((name, value) -> {
            jwtService.addTokenToBlacklist(value);
            headers.add(SET_COOKIE, clearCookie(name)); 
        });
        return headers;
    }
    
    @Override
    public Map<String, String> extractJwtTokens(Cookie[] cookies) {
        if (nonNull(cookies)) {
            return stream(cookies)
                    .filter(cookie -> (cookie.getName().equals(ACCESS_TOKEN) || cookie.getName().equals(REFRESH_TOKEN))
                            && startsWithIgnoreCase(cookie.getValue(), BEARER_TOKEN_TYPE))
                    .map(cookie -> {
                        final String jwtToken = decode(cookie.getValue().substring(7), UTF_8);
                        return Map.entry(cookie.getName(), jwtToken);
                    }).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return emptyMap();
        }
    }
    
    private String clearCookie(String name) {   
        return buildCookie(name, EMPTY_DELIMITER, 0);
    }
 
}