package com.system.vetcare.service;

import com.system.vetcare.domain.Manager;

public interface ManagerService {

    Manager findByUserId(Integer id);

}
