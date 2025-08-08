package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.vetcare.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer>{

    Manager findByUserId(Integer id);

}