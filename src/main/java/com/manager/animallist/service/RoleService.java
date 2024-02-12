package com.manager.animallist.service;

import java.util.Set;
import com.manager.animallist.domain.Role;

public interface RoleService {

    Set<Role> getByRoleNames(Set<String> roleNames);
    
}