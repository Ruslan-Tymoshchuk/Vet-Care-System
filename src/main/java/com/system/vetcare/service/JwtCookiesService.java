package com.system.vetcare.service;

import java.util.Map;
import javax.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;

public interface JwtCookiesService {

	HttpHeaders issueJwtCookies(String email);
	
	HttpHeaders refreshJwtCookies(Cookie[] cookies);

    HttpHeaders revokeJwtCookies(Cookie[] cookies);

    Map<String, String> extractJwtTokens(Cookie[] cookies);

}