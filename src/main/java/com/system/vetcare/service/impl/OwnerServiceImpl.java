package com.system.vetcare.service.impl;

import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Owner;
import com.system.vetcare.repository.OwnerRepository;
import com.system.vetcare.service.OwnerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    
    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }
    
    @Override
    public Owner findByUserId(Integer id) {
       return ownerRepository.findByUserId(id);
    }

}