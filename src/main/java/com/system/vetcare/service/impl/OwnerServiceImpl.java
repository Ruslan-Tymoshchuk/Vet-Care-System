package com.system.vetcare.service.impl;

import static java.lang.String.format;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Owner;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.repository.OwnerRepository;
import com.system.vetcare.service.OwnerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    public static final String OWNER_WITH_USER_ID_NOT_FOUND = "Owner with [user id: %s] not found.";
    public static final String OWNER_WITH_ID_NOT_FOUND = "Owner with [id: %s] not found.";

    private final OwnerRepository ownerRepository;

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Owner findByUserId(Integer id) {
        return ownerRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(format(OWNER_WITH_USER_ID_NOT_FOUND, id)));
    }

    @Override
    public Owner findById(Integer id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(OWNER_WITH_ID_NOT_FOUND, id)));
    }

}