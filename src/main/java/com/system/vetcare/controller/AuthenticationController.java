package com.system.vetcare.controller;

import static org.springframework.http.HttpStatus.*;
import static com.system.vetcare.controller.constants.AuthenticationUrl.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.request.AuthenticationRequest;
import com.system.vetcare.payload.request.RegistrationRequest;
import com.system.vetcare.payload.request.UserEmailValidationRequest;
import com.system.vetcare.payload.response.AuthenticationResponse;
import com.system.vetcare.payload.response.UserEmailValidationResponse;
import com.system.vetcare.service.AuthenticationService;
import com.system.vetcare.service.JwtCookiesService;
import com.system.vetcare.service.UserService;
import com.system.vetcare.service.UsernameValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(SECURITY_API_PATH)
public class AuthenticationController {

    private final UserService userService;
    private final JwtCookiesService jwtCookiesService;
    private final UsernameValidator usernameValidator;
    private final AuthenticationService authenticationService;

    @PostMapping(USER_REGISTRATION)
    public ResponseEntity<AuthenticationResponse> performRegistration(
            @RequestBody RegistrationRequest registrationRequest) {
        User user = userService.save(registrationRequest);
        HttpHeaders headers = jwtCookiesService.issueJwtCookies(user.getEmail());
        AuthenticationResponse authenticationResponse = authenticationService.buildAuthenticationResponse(user);
        return ResponseEntity
                .status(CREATED)
                .headers(headers)
                .body(authenticationResponse);
    }

    @PostMapping(USER_LOGIN)
    public ResponseEntity<AuthenticationResponse> performLogIn(@RequestBody AuthenticationRequest credential) {
        User user = authenticationService.resolvePrincipal(credential);
        AuthenticationResponse authenticationResponse = authenticationService.buildAuthenticationResponse(user);
        HttpHeaders headers = jwtCookiesService.issueJwtCookies(user.getEmail());
        return ResponseEntity.ok()
                .headers(headers)
                .body(authenticationResponse);
    }
    
    @PostMapping(REFRESH_JWT_TOKEN)
    public ResponseEntity<Void> performRefreshToken(HttpServletRequest request) {
        HttpHeaders headers = jwtCookiesService.refreshJwtCookies(request.getCookies());
        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @PostMapping(USER_LOGOUT)
    public ResponseEntity<Void> performLogOut(HttpServletRequest request) {
        authenticationService.revokePrincipalAuthentication();
        HttpHeaders headers = jwtCookiesService.revokeJwtCookies(request.getCookies());
        return ResponseEntity
                .status(NO_CONTENT)
                .headers(headers)
                .build();
    }

    @PostMapping(VALIDATE_EMAIL)
    public ResponseEntity<UserEmailValidationResponse> validateUserEmail(
            @RequestBody UserEmailValidationRequest userEmailValidationRequest) {
        return ResponseEntity.ok(usernameValidator.usernameIsAlreadyTaken(userEmailValidationRequest));
    }
    
}