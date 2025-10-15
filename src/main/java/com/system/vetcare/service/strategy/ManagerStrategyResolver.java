package com.system.vetcare.service.strategy;

import static com.system.vetcare.domain.enums.EAuthority.MANAGER;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Manager;
import com.system.vetcare.domain.Staff;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.payload.response.UserProfileDetails;
import com.system.vetcare.service.ManagerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerStrategyResolver implements UserProfileResolverStrategy {

    private final ManagerService managerService;

    @Override
    public EAuthority getSupportedAuthority() {
        return MANAGER;
    }

    @Override
    public UserProfileDetails resolveUserProfileDetails(Integer userId) {
        Manager manager = managerService.findByUserId(userId);
        return new UserProfileDetails(manager.getId(), getSupportedAuthority().name());
    }

    @Override
    public void saveProfileForUser(User user) {
        managerService.save(Manager
                              .builder()
                              .staff(Staff
                                       .builder()
                                       .user(user)
                                       .build())
                              .build());
    }

}