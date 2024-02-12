package com.manager.animallist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.animallist.domain.DUser;

@Repository
public interface UserRepository extends JpaRepository<DUser, Integer>{
    
    Optional<DUser> findByEmail(String name);
    
    boolean existsByEmail(String name);
    
}