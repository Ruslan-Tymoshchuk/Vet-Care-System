package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.vetcare.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer>{

    Owner findByUserId(Integer id);

}