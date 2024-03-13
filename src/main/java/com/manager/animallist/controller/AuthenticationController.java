package com.manager.animallist.controller;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static com.manager.animallist.payload.JWTMarkers.*;
import static org.springframework.http.HttpStatus.CREATED;
import static java.util.UUID.randomUUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public void performLogout(HttpServletRequest request) {
        authenticationService.logout(request);
    }

    @PostMapping("/validate_email")
    public UserEmailValidationResponse validateUserEmail(
            @RequestBody UserEmailValidationRequest userEmailValidationRequest) {
        return usernameValidator.usernameIsAlreadyTaken(userEmailValidationRequest);
    }

    private void addUserJwtCookies(HttpServletResponse response, String userEmail) {
        response.addHeader(SET_COOKIE, jwtService.createJwtCookie(ACCESS_TOKEN, jwtService.generateToken(userEmail)));
        response.addHeader(SET_COOKIE,
                jwtService.createJwtCookie(REFRESH_TOKEN, jwtService.generateToken(randomUUID().toString())));
    }
}