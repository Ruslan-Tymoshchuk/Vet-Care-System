package com.system.vetcare.security.controller;

import static org.springframework.http.HttpStatus.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.RegistrationRequest;
import com.system.vetcare.payload.UserEmailValidationRequest;
import com.system.vetcare.payload.UserEmailValidationResponse;
import com.system.vetcare.security.payload.AuthenticationRequest;
import com.system.vetcare.security.payload.AuthenticationResponse;
import com.system.vetcare.security.service.AuthenticationService;
import com.system.vetcare.service.CookiesService;
import com.system.vetcare.service.UserService;
import com.system.vetcare.service.UsernameValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String URL_NEW_USER_REGISTRATION = "/api/v1/auth/registration";
    public static final String URL_USER_LOGIN = "/api/v1/auth/login";
    public static final String URL_USER_LOGOUT = "/api/v1/auth/logout";
    public static final String URL_VALIDATE_EMAIL = "/api/v1/auth/validate_email";
    
    private final UserService userService;
    private final CookiesService cookieService;
    private final UsernameValidator usernameValidator;
    private final AuthenticationService authenticationService;

    @PostMapping(URL_NEW_USER_REGISTRATION)
    public ResponseEntity<AuthenticationResponse> performRegistration(
            @RequestBody RegistrationRequest registrationRequest) {
        User user = userService.save(registrationRequest);
        HttpHeaders headers = cookieService.issueJwtCookies(user);
        AuthenticationResponse authenticationResponse = authenticationService.buildAuthenticationResponse(user);
        return ResponseEntity
                .status(CREATED)
                .headers(headers)
                .body(authenticationResponse);
    }

    @PostMapping(URL_USER_LOGIN)
    public ResponseEntity<AuthenticationResponse> performLogIn(@RequestBody AuthenticationRequest credential,
            HttpServletRequest request) {
        User user = authenticationService.resolvePrincipal(credential);
        AuthenticationResponse authenticationResponse = authenticationService.buildAuthenticationResponse(user);
        HttpHeaders headers = cookieService.issueJwtCookies(user);
        return ResponseEntity.ok()
                .headers(headers)
                .body(authenticationResponse);
    }

    @PostMapping(URL_USER_LOGOUT)
    public ResponseEntity<Void> performLogOut(HttpServletRequest request) {
        authenticationService.revokePrincipalAuthentication();
        HttpHeaders headers = cookieService.revokeJwtCookies(request.getCookies());
        return ResponseEntity
                .status(NO_CONTENT)
                .headers(headers)
                .build();
    }

    @PostMapping(URL_VALIDATE_EMAIL)
    public ResponseEntity<UserEmailValidationResponse> validateUserEmail(
            @RequestBody UserEmailValidationRequest userEmailValidationRequest) {
        return ResponseEntity.ok(usernameValidator.usernameIsAlreadyTaken(userEmailValidationRequest));
    }
    
}