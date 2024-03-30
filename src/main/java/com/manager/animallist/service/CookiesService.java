package com.manager.animallist.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface CookiesService {

    Cookie buildCookie(String cookieName, String cookieValue, Integer cookieLifeTime);

    void addUserJwtCookies(HttpServletResponse response, String email);

}