package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.system.vetcare.domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

    Manager findByUserId(Integer id);

}