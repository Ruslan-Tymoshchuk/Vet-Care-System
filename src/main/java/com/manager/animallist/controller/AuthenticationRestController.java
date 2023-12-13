package com.manager.animallist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manager.animallist.dto.PersonDto;
import com.manager.animallist.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestController {

    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;
    
    public AuthenticationRestController(UserService userService,
            AuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
      }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void performAuthenticate(@RequestBody PersonDto personDto) {
        Authentication authentication = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(personDto.getName(), personDto.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void performRegistration(@RequestBody PersonDto personDto) {
        userService.registerNewPerson(personDto);
        Authentication authentication = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(personDto.getName(), personDto.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }
}