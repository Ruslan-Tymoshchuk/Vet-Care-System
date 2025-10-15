package com.system.vetcare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.system.vetcare.domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

    Optional<Manager> findByStaff_UserId(Integer id);

}