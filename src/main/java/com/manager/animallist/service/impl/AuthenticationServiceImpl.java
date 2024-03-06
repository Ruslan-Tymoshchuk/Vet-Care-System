package com.manager.animallist.service.impl;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.manager.animallist.domain.DUser;
import com.manager.animallist.payload.AuthenticationRequest;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.repository.UserRepository;
import com.manager.animallist.service.AuthenticationService;
import com.manager.animallist.service.JwtService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String USER_IS_DOES_NOT_EXISTS = "The user with email: %s is doesn't exists.";
    public static final String ACCOUNT_HAS_BEEN_LOCKED_DUE_TO_FAILED_ATTEMPTS = "Your account has been locked due to %d failed attempts. "
            + "It will be unlocked after %d minutes.";
    public static final String INCORRECT_PASSWORD = "Incorrect password! You taken %s attempts";
    public static final String ACCOUNT_HAS_BEEN_LOCKED = "Your account has been locked";

    @Value("${lock.duration.minutes}")
    private int lockDurationMinutes;
    @Value("${max.failed.attempts}")
    private int maxFailedAttempts;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final Map<String, Integer> failedAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> accountlockTime = new HashMap<>();

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        DUser user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format(USER_IS_DOES_NOT_EXISTS, authenticationRequest.getEmail())));
        validateAuthenticationRequest(authenticationRequest, user);
        user.setDtLogin(now());
        return AuthenticationResponse.builder().email(user.getEmail())
                .roles(user.getRoles().stream().map(role -> role.getRoleType().name()).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(AUTHORIZATION);
        String jwtToken = requestTokenHeader.substring(7);
        jwtService.addTokenToBlacklist(jwtToken);
    }

    private void validateAuthenticationRequest(AuthenticationRequest authenticationRequest, DUser user) {
        if (user.isAccountNonLocked()) {
            if (accountlockTime.containsKey(user.getEmail()) && accountlockTime.get(user.getEmail()).isAfter(now())) {
                throw new LockedException(String.format(ACCOUNT_HAS_BEEN_LOCKED_DUE_TO_FAILED_ATTEMPTS,
                        maxFailedAttempts, MINUTES.between(now(), accountlockTime.get(user.getEmail())) % 60));
            }
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                int actualFailedAttempts = failedAttempts.merge(user.getEmail(), 1, Integer::sum);
                if (failedAttempts.get(user.getEmail()) == maxFailedAttempts) {
                    accountlockTime.put(user.getEmail(), now().plusMinutes(lockDurationMinutes));
                    failedAttempts.put(user.getEmail(), 0);
                }
                throw new BadCredentialsException(String.format(INCORRECT_PASSWORD, actualFailedAttempts));
            } else {
                failedAttempts.put(user.getEmail(), 0);
            }
        } else {
            throw new LockedException(ACCOUNT_HAS_BEEN_LOCKED);
        }
    }
}