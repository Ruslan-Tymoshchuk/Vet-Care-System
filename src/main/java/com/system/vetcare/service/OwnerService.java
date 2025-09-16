package com.system.vetcare.service;

import com.system.vetcare.domain.Owner;

public interface OwnerService {

    Owner save(Owner owner);
    
    Owner findByUserId(Integer id);

    Owner findById(Integer id);

}