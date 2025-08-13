package com.system.vetcare.service;

import java.util.List;
import com.system.vetcare.domain.Authority;
import com.system.vetcare.payload.AuthorityDetailsResponse;

public interface AuthorityService {

    List<AuthorityDetailsResponse> findAll();

    List<Authority> findAllById(List<Integer> authorityIds);
    
}