package com.manager.animallist.controller;

import static com.manager.animallist.payload.JWTMarkers.*;
import static org.springframework.http.HttpStatus.CREATED;
import static java.util.Arrays.stream;
import static java.lang.String.format;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.UUID.randomUUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.manager.animallist.payload.AuthenticationRequest;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;
import com.manager.animallist.payload.UserEmailValidationRequest;
import com.manager.animallist.payload.UserEmailValidationResponse;
import com.manager.animallist.service.AuthenticationService;
import com.manager.animallist.service.JwtService;
import com.manager.animallist.service.UserService;
import com.manager.animallist.service.UsernameValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    public static final String JWT_FORMAT = "%s %s";
    public static final String SPACE_DELIMITER = " ";

    @Value("${access.token.life.time}")
    private Integer accessTokenLifeTime;
    @Value("${refresh.token.life.time}")
    private Integer refreshTokenLifeTime;
    @Value("${jwt.cookie.life.time}")
    private Integer jwtCookieLifeTime;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UsernameValidator usernameValidator;

    @PostMapping("/registration")
    @ResponseStatus(CREATED)
    public AuthenticationResponse performRegistration(@RequestBody RegistrationRequest registrationRequest,
            HttpServletResponse response) {
        addUserJwtCookies(response, registrationRequest.getEmail());
        return userService.saveNewUser(registrationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse performAuthenticate(@RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) {
        addUserJwtCookies(response, authenticationRequest.getEmail());
        return authenticationService.login(authenticationRequest);
    }

    @GetMapping("/logout")
    public void performLogout(HttpServletRequest request, HttpServletResponse response) {
        stream(request.getCookies()).forEach(cookie -> {
            String cookieName = cookie.getName();
            Cookie cookieToDelete = buildCookie(cookieName, SPACE_DELIMITER, 0);
            response.addCookie(cookieToDelete);
        });
        authenticationService.logout(request);
    }

    @PostMapping("/validate_email")
    public UserEmailValidationResponse validateUserEmail(
            @RequestBody UserEmailValidationRequest userEmailValidationRequest) {
        return usernameValidator.usernameIsAlreadyTaken(userEmailValidationRequest);
    }

    private void addUserJwtCookies(HttpServletResponse response, String userEmail) {
        response.addCookie(buildCookie(ACCESS_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE, jwtService.generateToken(userEmail, accessTokenLifeTime)),
                       jwtCookieLifeTime));
        response.addCookie(
                buildCookie(REFRESH_TOKEN,
                format(JWT_FORMAT, BEARER_TOKEN_TYPE,
                       jwtService.generateToken(randomUUID().toString(), refreshTokenLifeTime)),
                       jwtCookieLifeTime));
    }

    private Cookie buildCookie(String cookieName, String cookieValue, Integer cookieLifeTime) {
        Cookie cookie = new Cookie(cookieName, encode(cookieValue, UTF_8));
        cookie.setMaxAge(cookieLifeTime);
        cookie.setHttpOnly(true);
        return cookie;
    }
}