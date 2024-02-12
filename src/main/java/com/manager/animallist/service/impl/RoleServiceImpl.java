
package com.manager.animallist.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.manager.animallist.domain.EUserRole;
import com.manager.animallist.domain.Role;
import com.manager.animallist.repository.RoleRepository;
import com.manager.animallist.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getByRoleNames(Set<String> roleNames) {
        Set<Role> userRoles = new HashSet<>();
        roleNames.forEach(roleName -> {
            Role userRole = roleRepository.getRoleByRoleType(EUserRole.valueOf(roleName))
                    .orElseThrow(NoSuchElementException::new);
            userRoles.add(userRole);
        });
        return userRoles;
    }
}