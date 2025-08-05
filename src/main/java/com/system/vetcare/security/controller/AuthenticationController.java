package com.system.vetcare.security.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static java.util.Arrays.stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.system.vetcare.payload.AuthenticationRequest;
import com.system.vetcare.payload.AuthenticationResponse;
import com.system.vetcare.payload.RegistrationRequest;
import com.system.vetcare.payload.UserEmailValidationRequest;
import com.system.vetcare.payload.UserEmailValidationResponse;
import com.system.vetcare.service.AuthenticationService;
import com.system.vetcare.service.CookiesService;
import com.system.vetcare.service.UserService;
import com.system.vetcare.service.UsernameValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    public static final String SPACE_DELIMITER = " ";

    private final UserService userService;
    private final CookiesService cookieService;
    private final UsernameValidator usernameValidator;
    private final AuthenticationService authenticationService;
   
    @PostMapping("/registration")
    @ResponseStatus(CREATED)
    public AuthenticationResponse performRegistration(@RequestBody RegistrationRequest registrationRequest,
            HttpServletResponse response) {
        cookieService.addUserJwtCookies(response, registrationRequest.getEmail());
        return userService.saveNewUser(registrationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse performAuthenticate(@RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) {
        cookieService.addUserJwtCookies(response, authenticationRequest.getEmail());
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/logout")
    public void performLogout(HttpServletRequest request, HttpServletResponse response) {
        stream(request.getCookies()).forEach(cookie -> {
            String cookieName = cookie.getName();
            Cookie cookieToDelete = cookieService.buildCookie(cookieName, SPACE_DELIMITER, 0);
            response.addCookie(cookieToDelete);
        });
        authenticationService.logout(request);
    }
    
    @PostMapping("/validate_email")
    public UserEmailValidationResponse validateUserEmail(
            @RequestBody UserEmailValidationRequest userEmailValidationRequest) {
        return usernameValidator.usernameIsAlreadyTaken(userEmailValidationRequest);
    }
}