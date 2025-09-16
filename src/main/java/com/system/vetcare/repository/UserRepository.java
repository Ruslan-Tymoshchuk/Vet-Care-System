package com.system.vetcare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.system.vetcare.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.authorities WHERE u.email = :email")
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String name);
    
}