
package com.manager.animallist.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.manager.animallist.domain.EAuthority;
import com.manager.animallist.domain.Authority;
import com.manager.animallist.repository.AuthorityRepository;
import com.manager.animallist.service.AuthorityService;

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