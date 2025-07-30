package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.vetcare.domain.Authority;
import com.system.vetcare.domain.EAuthority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    
    Optional<Authority> findByTitle(EAuthority title);
    
}