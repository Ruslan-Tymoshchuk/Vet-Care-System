package com.system.vetcare.service.impl;

import static java.time.LocalDateTime.now;
import static java.lang.String.format;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.system.vetcare.domain.User;
import com.system.vetcare.payload.RegistrationRequest;
import com.system.vetcare.repository.UserRepository;
import com.system.vetcare.security.strategy.UserProfileResolver;
import com.system.vetcare.service.AuthorityService;
import com.system.vetcare.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_EMAIL_DOES_NOT_EXISTS = "The user with email: %s is doesn't exists.";
    
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final UserProfileResolver userProfileResolver;
    private final PasswordEncoder passwordEncoder;
   
    @Override
    @Transactional
    public User save(RegistrationRequest registrationRequest) {   
        User user = userRepository
                .save(User
                        .builder()
                        .firstName(registrationRequest.firstName())
                        .lastName(registrationRequest.lastName())
                        .email(registrationRequest.email())
                        .password(passwordEncoder.encode(registrationRequest.password()))
                        .legalCertificateId(registrationRequest.legalCertificateId())
                        .authorities(authorityService.findAllById(registrationRequest.authorityIds()))
                        .accountNonLocked(true)
                        .lastLogin(now())
                        .build()); 
        userProfileResolver.saveUserProfiles(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format(USER_EMAIL_DOES_NOT_EXISTS, email)));
    }

    @Override
    @Transactional
    public void updateLoginTimestamp(User user) {
        user.setLastLogin(now());
    }
   
}