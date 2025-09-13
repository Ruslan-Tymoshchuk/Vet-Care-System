package com.system.vetcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import com.system.vetcare.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http.csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   .and()
                   .anonymous().disable()
                   .addFilterBefore(jwtAuthFilter, FilterSecurityInterceptor.class)
                   .authorizeHttpRequests()
                   .antMatchers("/api/v1/authentication/login").permitAll()
                   .antMatchers("/api/v1/authorities/all").permitAll()
                   .antMatchers("/api/v1/authentication/registration").permitAll()
                   .antMatchers("/api/v1/authentication/validate_email").permitAll()
                   .anyRequest()
                   .authenticated();
        return http.build();
    }
    
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}