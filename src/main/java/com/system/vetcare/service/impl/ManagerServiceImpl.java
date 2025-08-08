package com.system.vetcare.service.impl;

import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Manager;
import com.system.vetcare.repository.ManagerRepository;
import com.system.vetcare.service.ManagerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    
    @Override
    public Manager findByUserId(Integer id) {
        return managerRepository.findByUserId(id);
    }

}