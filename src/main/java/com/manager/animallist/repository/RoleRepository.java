package com.manager.animallist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.manager.animallist.domain.EUserRole;
import com.manager.animallist.domain.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Optional<Role> getRoleByRoleType(EUserRole role);
    
}