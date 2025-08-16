package com.system.vetcare.security.service.impl;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static org.springframework.web.util.WebUtils.getCookie;
import static com.system.vetcare.payload.JwtMarkers.ACCESS_TOKEN;
import static com.system.vetcare.payload.JwtMarkers.BEARER_TOKEN_TYPE;
import static com.system.vetcare.payload.JwtMarkers.REFRESH_TOKEN;
import static java.lang.String.format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.system.vetcare.domain.User;
import com.system.vetcare.security.payload.AuthenticationRequest;
import com.system.vetcare.security.payload.AuthenticationResponse;
import com.system.vetcare.security.payload.UserProfileDetails;
import com.system.vetcare.security.service.AuthenticationService;
import com.system.vetcare.security.strategy.UserProfileResolver;
import com.system.vetcare.service.JwtService;
import com.system.vetcare.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
    private final UserProfileResolver authorityResolver;
    
    @Override
    public AuthenticationResponse buildAuthenticationResponse(User user) {
        List<UserProfileDetails> userAuthorities = authorityResolver.resolveUserProfiles(user);          
        return new AuthenticationResponse(user.getEmail(), user.getLastLogin().format(TIME_FORMATTER), userAuthorities);
    }
    
    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        User user = validateCredentials(authenticationRequest);
        user = userService.updateLoginTimestamp(user);
        return buildAuthenticationResponse(user);
    }
    
    private User validateCredentials(AuthenticationRequest authenticationRequest) {
        UserDetails user = userService.loadUserByUsername(authenticationRequest.email()); 
        if (user.isAccountNonLocked()) {
            if (accountlockTime.containsKey(user.getUsername()) && accountlockTime.get(user.getUsername()).isAfter(now())) {
                throw new LockedException(format(ACCOUNT_HAS_BEEN_LOCKED_DUE_TO_FAILED_ATTEMPTS, maxFailedAttempts,
                        MINUTES.between(now(), accountlockTime.get(user.getUsername())) % 60));
            }
            if (!passwordEncoder.matches(authenticationRequest.password(), user.getPassword())) {
                int actualFailedAttempts = failedAttempts.merge(user.getUsername(), 1, Integer::sum);
                if (failedAttempts.get(user.getUsername()) == maxFailedAttempts) {
                    accountlockTime.put(user.getUsername(), now().plusMinutes(lockDurationMinutes));
                    failedAttempts.put(user.getUsername(), 0);
                }
                throw new BadCredentialsException(format(INCORRECT_PASSWORD, actualFailedAttempts));
            } else {
                failedAttempts.put(user.getUsername(), 0);  
                return (User) user;
            }
        } else {
            throw new LockedException(ACCOUNT_HAS_BEEN_LOCKED);
        }    
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
 
}