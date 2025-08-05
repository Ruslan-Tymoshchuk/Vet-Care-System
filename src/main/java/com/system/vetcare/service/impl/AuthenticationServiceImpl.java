package com.system.vetcare.service.impl;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static org.springframework.web.util.WebUtils.getCookie;
import static com.system.vetcare.payload.JwtMarkers.ACCESS_TOKEN;
import static com.system.vetcare.payload.JwtMarkers.BEARER_TOKEN_TYPE;
import static com.system.vetcare.payload.JwtMarkers.REFRESH_TOKEN;
import static java.lang.String.format;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.AuthenticationRequest;
import com.system.vetcare.payload.AuthenticationResponse;
import com.system.vetcare.service.AuthenticationService;
import com.system.vetcare.service.JwtService;
import com.system.vetcare.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

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
    private final UserService userService;
    private final Map<String, Integer> failedAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> accountlockTime = new HashMap<>();

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        User user = userService.findByEmail(authenticationRequest.getEmail());
        validateAuthenticationRequest(authenticationRequest, user);
        user.setDtLogin(now());
        return AuthenticationResponse.builder().email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        Cookie accessToken = getCookie(request, ACCESS_TOKEN);
        Cookie refreshToken = getCookie(request, REFRESH_TOKEN);
        if (accessToken != null && refreshToken != null
                && startsWithIgnoreCase(accessToken.getValue(), BEARER_TOKEN_TYPE)
                && startsWithIgnoreCase(refreshToken.getValue(), BEARER_TOKEN_TYPE)) {
            jwtService.addTokenToBlacklist(accessToken.getValue().substring(7));
            jwtService.addTokenToBlacklist(refreshToken.getValue().substring(7));
        }
    }

    private void validateAuthenticationRequest(AuthenticationRequest authenticationRequest, User user) {
        if (user.isAccountNonLocked()) {
            if (accountlockTime.containsKey(user.getEmail()) && accountlockTime.get(user.getEmail()).isAfter(now())) {
                throw new LockedException(format(ACCOUNT_HAS_BEEN_LOCKED_DUE_TO_FAILED_ATTEMPTS,
                        maxFailedAttempts, MINUTES.between(now(), accountlockTime.get(user.getEmail())) % 60));
            }
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                int actualFailedAttempts = failedAttempts.merge(user.getEmail(), 1, Integer::sum);
                if (failedAttempts.get(user.getEmail()) == maxFailedAttempts) {
                    accountlockTime.put(user.getEmail(), now().plusMinutes(lockDurationMinutes));
                    failedAttempts.put(user.getEmail(), 0);
                }
                throw new BadCredentialsException(format(INCORRECT_PASSWORD, actualFailedAttempts));
            } else {
                failedAttempts.put(user.getEmail(), 0);
            }
        } else {
            throw new LockedException(ACCOUNT_HAS_BEEN_LOCKED);
        }
    }
}