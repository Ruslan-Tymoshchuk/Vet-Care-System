package com.system.vetcare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.system.vetcare.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer>{

    Optional<Owner> findByUserId(Integer id);

}