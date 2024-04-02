package com.manager.animallist.service.impl;

import static com.manager.animallist.payload.JwtMarkers.ACCESS_TOKEN;
import static com.manager.animallist.payload.JwtMarkers.BEARER_TOKEN_TYPE;
import static com.manager.animallist.payload.JwtMarkers.REFRESH_TOKEN;
import static java.lang.String.format;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.manager.animallist.service.CookiesService;
import com.manager.animallist.service.JwtService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookiesServiceImpl implements CookiesService {

    public static final String JWT_FORMAT = "%s %s";
    
    private final JwtService jwtService;
    @Value("${access.token.life.time}")
    private Integer accessTokenLifeTime;
    @Value("${refresh.token.life.time}")
    private Integer refreshTokenLifeTime;
    @Value("${jwt.cookie.life.time}")
    private Integer jwtCookieLifeTime;
    
    @Override
    public Cookie buildCookie(String cookieName, String cookieValue, Integer cookieLifeTime) {
        Cookie cookie = new Cookie(cookieName, encode(cookieValue, UTF_8));
        cookie.setMaxAge(cookieLifeTime);
        cookie.setHttpOnly(true);
        return cookie;
    }  
    
    @Override
    public void addUserJwtCookies(HttpServletResponse response, String email) {
        response.addCookie(buildCookie(ACCESS_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE, jwtService.generateToken(email, accessTokenLifeTime)),
                       jwtCookieLifeTime));
        response.addCookie(
                buildCookie(REFRESH_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE,
                       jwtService.generateToken(email, refreshTokenLifeTime)),
                       jwtCookieLifeTime));
    }
}