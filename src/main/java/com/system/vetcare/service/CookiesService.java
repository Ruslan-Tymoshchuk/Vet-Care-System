package com.system.vetcare.service;

import javax.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import com.system.vetcare.domain.User;

public interface CookiesService {

    String buildCookie(String cookieName, String cookieValue, Integer cookieLifeTime);

    HttpHeaders revokeJwtCookies(Cookie[] cookies);

    HttpHeaders issueJwtCookies(User user);

}