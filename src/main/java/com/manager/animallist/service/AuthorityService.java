package com.manager.animallist.service;

import java.util.Set;
import com.manager.animallist.domain.Authority;

public interface AuthorityService {

    Set<Authority> findAuthorities(Set<String> titles);
    
}