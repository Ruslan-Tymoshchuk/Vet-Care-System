
package com.system.vetcare.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Authority;
import com.system.vetcare.payload.AuthorityDetailsResponse;
import com.system.vetcare.repository.AuthorityRepository;
import com.system.vetcare.service.AuthorityService;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public List<AuthorityDetailsResponse> findAll() {
        return authorityRepository.findAll().stream()
                .map(authority -> new AuthorityDetailsResponse(authority.getId(), authority.getAuthority())).toList();
    }

    @Override
    public List<Authority> findAllById(List<Integer> authorityIds) {
        return authorityRepository.findAllById(authorityIds);
    }

}