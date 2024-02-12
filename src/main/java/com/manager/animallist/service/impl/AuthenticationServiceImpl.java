package com.manager.animallist.service.impl;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.manager.animallist.payload.AuthenticationRequest;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.service.AuthenticationService;
import com.manager.animallist.service.JwtService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${lock.duration.minutes}")
    private int lockDurationMinutes;
    @Value("${max.filed.attempts}")
    private int maxFiledAttempts;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final Map<String, Integer> filedAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> accountlockTime = new HashMap<>();

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
       UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        validateAuthenticationRequest(authenticationRequest, userDetails);
        return AuthenticationResponse.builder().email(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toSet()))
                .build();
    }
    
    @Override
    public void logout(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(AUTHORIZATION);
        String jwtToken = requestTokenHeader.substring(7);
        jwtService.addTokenToBlacklist(jwtToken);
    } 
    
    private void validateAuthenticationRequest(AuthenticationRequest authenticationRequest, UserDetails userDetails) {
        if (userDetails.isAccountNonLocked()) {
            if (accountlockTime.containsKey(userDetails.getUsername()) && accountlockTime.get(userDetails.getUsername()).isAfter(now())) {
                throw new LockedException("Your account has been locked due to " + maxFiledAttempts
                        + " failed attempts. It will be unlocked after "
                        + MINUTES.between(now(), accountlockTime.get(userDetails.getUsername())) % 60 + " minutes.");
            }
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
                int actualFailedAttempts = filedAttempts.merge(userDetails.getUsername(), 1, Integer::sum);
                if (filedAttempts.get(userDetails.getUsername()) == maxFiledAttempts) {
                    accountlockTime.put(userDetails.getUsername(), now().plusMinutes(lockDurationMinutes));
                    filedAttempts.put(userDetails.getUsername(), 0);
                }
                throw new BadCredentialsException(
                        "Incorrect password! You taken " + actualFailedAttempts + " attempts");
            } else {
                filedAttempts.put(userDetails.getUsername(), 0);
            }
        } else {
            throw new LockedException("Your account has been locked");
        }
    }
}