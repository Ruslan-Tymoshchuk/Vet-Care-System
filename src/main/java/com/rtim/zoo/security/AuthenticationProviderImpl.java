package com.rtim.zoo.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.rtim.zoo.dto.PersonDto;
import com.rtim.zoo.service.UserService;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    
    @Value("${max.filed.attempts}")
    private int maxFiledAttempts;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
  
    public AuthenticationProviderImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PersonDto personDetails = userService.getByName(authentication.getName());
        String password = authentication.getCredentials().toString();
        if (personDetails.isAccountNonLocked() || userService.unlockWhenTimeExpired(personDetails)) {
            if (!passwordEncoder.matches(password, personDetails.getPassword())) {
                if (personDetails.getFailedAttempt() >= maxFiledAttempts) {
                    userService.lock(personDetails);
                    throw new LockedException("Your account will be locked");
                } else {
                    userService.increaseFailedAttempts(personDetails);
                }
                throw new BadCredentialsException("Incorrect password");
            } else {
                userService.resetFailedAttempts(personDetails.getName());
            }
        } else {
            throw new LockedException(
                    "Your account has been locked due to 10 failed attempts. It will be unlocked after over time.");
        }
        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }    
}