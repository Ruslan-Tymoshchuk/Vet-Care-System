package com.manager.animallist.controller.filter;

import static  org.springframework.web.util.WebUtils.getCookie;
import static  org.springframework.util.StringUtils.startsWithIgnoreCase;
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
import com.manager.animallist.service.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        Cookie accessToken = getCookie(request, "Access-token");
        if (accessToken!= null && startsWithIgnoreCase(accessToken.getValue(), "Bearer")) {
                final String jwtToken = accessToken.getValue().substring(7);
                if (!jwtService.isTokenInBlackList(jwtToken)){
                    final String userEmail = jwtService.extractUserEmail(jwtToken);   
                    if (!userEmail.isBlank() && SecurityContextHolder.getContext().getAuthentication() != null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                        if (jwtService.validate(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                } else {
                    return;
                }  
        }
            filterChain.doFilter(request, response);
    }
}