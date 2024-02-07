package com.manager.animallist.service.impl;

import static java.time.LocalDateTime.now;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.manager.animallist.domain.DUser;
import com.manager.animallist.payload.AuthenticationResponse;
import com.manager.animallist.payload.RegistrationRequest;
import com.manager.animallist.repository.UserRepository;
import com.manager.animallist.service.RoleService;
import com.manager.animallist.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
   
    @Override
    @Transactional
    public AuthenticationResponse saveNewUser(RegistrationRequest registrationRequest) {          
        DUser user = userRepository
                .save(DUser.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .roles(roleService.getByRoleNames(registrationRequest.getRoleNames()))
                .accountNonLocked(true)
                .dtLogin(now())
                .build());
        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(role -> role.getRoleType().toString()).collect(Collectors.toSet()))
                .build(); 
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        DUser user = userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        return new User(user.getEmail(), user.getPassword(), true, true, true, user.isAccountNonLocked(), 
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().name())).toList());
    }
}