package com.system.vetcare.security.strategy;

import static com.system.vetcare.domain.enums.EAuthority.MANAGER;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Manager;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserAuthorityDetails;
import com.system.vetcare.service.ManagerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerStrategyResolver implements AuthorityResolverStrategy {

    private final ManagerService managerService;

    @Override
    public EAuthority getSupportedAuthority() {
        return MANAGER;
    }
    
    @Override
    public UserAuthorityDetails resolveAuthorityDetails(Integer userId) {
        Manager manager = managerService.findByUserId(userId);
        return new UserAuthorityDetails(manager.getId(), getSupportedAuthority().name());
    }

}