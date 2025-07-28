package com.manager.animallist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.manager.animallist.domain.EAuthority;
import com.manager.animallist.domain.Authority;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    
    Optional<Authority> findByTitle(EAuthority title);
    
}