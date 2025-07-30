package com.system.vetcare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.vetcare.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByEmail(String name);
    
    boolean existsByEmail(String name);
    
}