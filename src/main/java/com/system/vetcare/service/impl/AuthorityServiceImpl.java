
package com.system.vetcare.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Service;

import com.system.vetcare.domain.Authority;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.repository.AuthorityRepository;
import com.system.vetcare.service.AuthorityService;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository roleRepository;

    @Override
    public Set<Authority> findAuthorities(Set<String> titles) {
        Set<Authority> authorities = new HashSet<>();
        titles.forEach(title -> {
            Authority authority = roleRepository.findByTitle(EAuthority.valueOf(title))
                    .orElseThrow(NoSuchElementException::new);
            authorities.add(authority);
        });
        return authorities;
    }

}