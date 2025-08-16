package com.system.vetcare.service;

import com.system.vetcare.domain.Manager;

public interface ManagerService {

    Manager save(Manager manager);
    
    Manager findByUserId(Integer id);

}