package com.system.vetcare.security.filter;

import static org.springframework.web.util.WebUtils.getCookie;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static com.system.vetcare.payload.JwtMarkers.*;
import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.system.vetcare.service.CookiesService;
import com.system.vetcare.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKENS_ARE_BLACKLISTED = "Tokens are blacklisted";
    
    private final JwtService jwtService;
    private final CookiesService cookieService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            validateAuthenticationTokenRequest(request, response);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private void validateAuthenticationTokenRequest(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessToken = getCookie(request, ACCESS_TOKEN);
        Cookie refreshToken = getCookie(request, REFRESH_TOKEN);
        if (accessToken != null && refreshToken != null
                && startsWithIgnoreCase(accessToken.getValue(), BEARER_TOKEN_TYPE)
                && startsWithIgnoreCase(refreshToken.getValue(), BEARER_TOKEN_TYPE)) {
            final String jwtAccessToken = decode(accessToken.getValue().substring(7), UTF_8);
            final String jwtRefreshToken = decode(refreshToken.getValue().substring(7), UTF_8);
            if (jwtService.isTokenBlacklisted(jwtAccessToken) && jwtService.isTokenBlacklisted(jwtRefreshToken)) {
                throw new JwtException(TOKENS_ARE_BLACKLISTED);
            }
            String email;
            try {
                email = jwtService.parseToken(jwtAccessToken).getSubject();
            } catch (ExpiredJwtException e) {
                email = jwtService.parseToken(jwtRefreshToken).getSubject();
                jwtService.addTokenToBlacklist(jwtAccessToken);
                jwtService.addTokenToBlacklist(jwtRefreshToken);
                cookieService.addUserJwtCookies(response, email);
            }
            if (!email.isBlank() && SecurityContextHolder.getContext().getAuthentication() != null) {
                setAuthentication(email, request);
            }
        }
    }

    private void setAuthentication(String email, HttpServletRequest request) {      
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    
}