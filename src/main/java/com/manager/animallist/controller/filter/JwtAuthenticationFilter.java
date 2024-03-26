package com.manager.animallist.controller.filter;

import static org.springframework.web.util.WebUtils.getCookie;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static com.manager.animallist.payload.JWTMarkers.*;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
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
import com.manager.animallist.service.JwtService;
import io.jsonwebtoken.JwtException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie accessToken = getCookie(request, ACCESS_TOKEN);
            Cookie refreshToken = getCookie(request, REFRESH_TOKEN);
            if (accessToken != null && refreshToken != null
                    && startsWithIgnoreCase(accessToken.getValue(), BEARER_TOKEN_TYPE)
                    && startsWithIgnoreCase(refreshToken.getValue(), BEARER_TOKEN_TYPE)) {
                final String jwtAccessToken = accessToken.getValue().substring(7);
                final String jwtRefreshToken = refreshToken.getValue().substring(7);
                if (!jwtService.isTokenBlacklisted(jwtAccessToken) && !jwtService.isTokenBlacklisted(jwtRefreshToken)) {
                    final String userEmail = jwtService.extractUserEmail(jwtAccessToken);
                    if (!userEmail.isBlank() && SecurityContextHolder.getContext().getAuthentication() != null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                        if (jwtService.validate(jwtAccessToken, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                } else {
                    response.setStatus(SC_UNAUTHORIZED);
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}