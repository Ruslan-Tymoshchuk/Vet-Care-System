package com.system.vetcare.security.filter;

import static com.system.vetcare.payload.JwtMarkers.*;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.system.vetcare.domain.User;
import com.system.vetcare.service.CookiesService;
import com.system.vetcare.service.JwtService;
import io.jsonwebtoken.JwtException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CookiesService cookiesService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            validateAccessTokenRequest(request);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

	private void validateAccessTokenRequest(HttpServletRequest request) {
		Map<String, String> jwtTokens = cookiesService.extractJwtTokens(request.getCookies());
		if (jwtTokens.containsKey(ACCESS_TOKEN)) {
			final String jwtAccessToken = jwtTokens.get(ACCESS_TOKEN);
			if (!jwtService.isTokenBlacklisted(jwtAccessToken)) {
				final String email = jwtService.parse(jwtAccessToken).getSubject();
				if (!email.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null) {
					User user = (User) userDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							user, null, user.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
	}
	
}