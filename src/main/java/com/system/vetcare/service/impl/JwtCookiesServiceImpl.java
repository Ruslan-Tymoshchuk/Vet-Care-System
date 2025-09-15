package com.system.vetcare.service.impl;

import static com.system.vetcare.payload.JwtMarkers.ACCESS_TOKEN;
import static com.system.vetcare.payload.JwtMarkers.BEARER_TOKEN_TYPE;
import static com.system.vetcare.payload.JwtMarkers.REFRESH_TOKEN;
import static com.system.vetcare.controller.constants.AuthenticationUrl.*;
import static java.lang.String.format;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static java.util.Collections.*;
import static java.util.Objects.nonNull;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import com.system.vetcare.service.JwtCookiesService;
import com.system.vetcare.service.JwtService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtCookiesServiceImpl implements JwtCookiesService {
   
    public static final String JWT_FORMAT = "%s %s";
    public static final String EMPTY_STRING = "";
    public static final String LIMIT_THE_SCOPE = "None";
    
    @Value("${access.token.life.time}")
    private Integer accessTokenLifeTime;
    @Value("${refresh.token.life.time}")
    private Integer refreshTokenLifeTime;
    @Value("${jwt.cookie.life.time}")
    private Integer jwtCookieLifeTime;
    private final JwtService jwtService;
    
    @Override
    public HttpHeaders issueJwtCookies(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SET_COOKIE, buildCookie(ACCESS_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE, jwtService.generateToken(email, accessTokenLifeTime)), ABSOLUTE_API_PATH,
                jwtCookieLifeTime));
        headers.add(SET_COOKIE, buildCookie(REFRESH_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE,
                        jwtService.generateToken(email, refreshTokenLifeTime)), SECURITY_API_PATH,
                        jwtCookieLifeTime));
        return headers;
    }
    
    @Override
    public HttpHeaders refreshJwtCookies(Cookie[] cookies) {
    	 HttpHeaders headers = new HttpHeaders();
         Map<String, String> jwtTokens = extractJwtTokens(cookies);
         if (jwtTokens.containsKey(REFRESH_TOKEN) && !jwtService.tokenIsBlacklisted(REFRESH_TOKEN)) {
 			final String refreshToken = jwtTokens.get(REFRESH_TOKEN);
 			final String email = jwtService.parse(refreshToken).getSubject();
 			jwtService.addTokenToBlacklist(refreshToken);
 			if(jwtTokens.containsKey(ACCESS_TOKEN)) {
 				final String accessToken = jwtTokens.get(ACCESS_TOKEN);
 				jwtService.addTokenToBlacklist(accessToken);
 			}
 			return issueJwtCookies(email);
         }
    	 return headers;
    }
    
	@Override
	public HttpHeaders revokeJwtCookies(Cookie[] cookies) {
		HttpHeaders headers = new HttpHeaders();
		Map<String, String> jwtTokens = extractJwtTokens(cookies);
		if (jwtTokens.containsKey(ACCESS_TOKEN)) {
			final String accessToken = jwtTokens.get(ACCESS_TOKEN);
			jwtService.addTokenToBlacklist(accessToken);
		} if (jwtTokens.containsKey(REFRESH_TOKEN)) {
			final String refreshToken = jwtTokens.get(REFRESH_TOKEN);
			jwtService.addTokenToBlacklist(refreshToken);
		}
		headers.add(SET_COOKIE, buildCookie(ACCESS_TOKEN, EMPTY_STRING, ABSOLUTE_API_PATH, 0));
		headers.add(SET_COOKIE, buildCookie(REFRESH_TOKEN, EMPTY_STRING, SECURITY_API_PATH, 0));
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
    
	 private String buildCookie(String name, String value, String path, Integer lifeTime) {  
	        return ResponseCookie
	        		.from(name, encode(value, UTF_8))
	                .httpOnly(true)
	                .secure(true)
	                .sameSite(LIMIT_THE_SCOPE)
	                .path(path)
	                .maxAge(lifeTime)
	                .build()
	                .toString();
	    }  
 
}