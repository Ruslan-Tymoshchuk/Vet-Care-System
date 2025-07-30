package com.manager.animallist.service.impl;

import static java.time.LocalDateTime.now;
import static java.lang.String.format;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.manager.animallist.domain.Pet;
import com.manager.animallist.domain.User;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;
import com.manager.animallist.repository.UserRepository;
import com.manager.animallist.service.AuthorityService;
import com.manager.animallist.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    public static final String USER_IS_DOES_NOT_EXISTS = "The user with email: %s is doesn't exists.";
    
    private final UserRepository userRepository;
    private final AuthorityService roleService;
    private final PasswordEncoder passwordEncoder;
   
    @Override
    @Transactional
    public AuthenticationResponse saveNewUser(RegistrationRequest registrationRequest) {          
        User user = userRepository
                .save(User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .authorities(roleService.findAuthorities(registrationRequest.getRoleNames()))
                .accountNonLocked(true)
                .dtLogin(now())
                .build());
        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build(); 
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void assignUser(Pet pet, String userEmail) {
        pet.setOwner(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(format(USER_IS_DOES_NOT_EXISTS, email)));
    }
    
}