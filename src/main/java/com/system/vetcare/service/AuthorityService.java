package com.system.vetcare.service;

import java.util.Set;

import com.system.vetcare.domain.Authority;

public interface AuthorityService {

    Set<Authority> findAuthorities(Set<String> titles);
    
}