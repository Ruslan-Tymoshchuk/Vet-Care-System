package com.manager.animallist.service.impl;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.manager.animallist.domain.DUser;
import com.manager.animallist.payload.AuthenticationRequest;
import com.manager.animallist.service.AuthenticationValidator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationValidatorImpl implements AuthenticationValidator {

    @Value("${lock.duration.minutes}")
    private int lockDurationMinutes;
    @Value("${max.filed.attempts}")
    private int maxFiledAttempts;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, Integer> filedAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> accountlockTime = new HashMap<>();

    @Override
    public void validateAuthenticationRequest(AuthenticationRequest authenticationRequest, DUser user) {
        if (user.isAccountNonLocked()) {
            if (accountlockTime.containsKey(user.getEmail()) && accountlockTime.get(user.getEmail()).isAfter(now())) {
                throw new LockedException("Your account has been locked due to " + maxFiledAttempts
                        + " failed attempts. It will be unlocked after "
                        + MINUTES.between(now(), accountlockTime.get(user.getEmail())) % 60 + " minutes.");
            }
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                int actualFailedAttempts = filedAttempts.merge(user.getEmail(), 1, Integer::sum);
                if (filedAttempts.get(user.getEmail()) == maxFiledAttempts) {
                    accountlockTime.put(user.getEmail(), now().plusMinutes(lockDurationMinutes));
                    filedAttempts.put(user.getEmail(), 0);
                }
                throw new BadCredentialsException(
                        "Incorrect password! You taken " + actualFailedAttempts + " attempts");
            } else {
                filedAttempts.put(user.getEmail(), 0);
            }
        } else {
            throw new LockedException("Your account has been locked");
        }
    }
}