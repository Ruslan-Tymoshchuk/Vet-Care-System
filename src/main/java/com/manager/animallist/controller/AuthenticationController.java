package com.manager.animallist.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.manager.animallist.payload.AuthenticationRequest;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;
import com.manager.animallist.service.JwtService;
import com.manager.animallist.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse performRegistration(@RequestBody RegistrationRequest registrationRequest,
            HttpServletResponse response) {
        String accessToken = jwtService.generateToken(registrationRequest.getEmail());
        response.addHeader(HttpHeaders.SET_COOKIE, jwtService.createJwtCookie(accessToken).toString());
        return userService.saveNewUser(registrationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse performAuthenticate(@RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) {
        String accessToken = jwtService.generateToken(authenticationRequest.getEmail());
        response.addHeader(HttpHeaders.SET_COOKIE, jwtService.createJwtCookie(accessToken).toString());
        return userService.login(authenticationRequest);
    }

    @GetMapping("/logout")
    public void performLogout(HttpServletRequest request) {
        userService.logout(request);
    }
}