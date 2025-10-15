package com.system.vetcare.service.impl;

import static java.lang.String.format;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Manager;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.repository.ManagerRepository;
import com.system.vetcare.service.ManagerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    public static final String MANAGER_WITH_USER_ID_NOT_FOUND = "Manager with [user id: %s] not found.";
    public static final String MANAGER_WITH_ID_NOT_FOUND = "Manager with [id: %s] not found.";

    private final ManagerRepository managerRepository;

    @Override
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public Manager findByUserId(Integer id) {
        return managerRepository.findByStaff_UserId(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MANAGER_WITH_USER_ID_NOT_FOUND, id)));
    }

    @Override
    public Manager findById(Integer id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MANAGER_WITH_ID_NOT_FOUND, id)));
    }

}