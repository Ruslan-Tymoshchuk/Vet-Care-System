package com.system.vetcare.service.strategy;

import static com.system.vetcare.domain.enums.EAuthority.OWNER;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Owner;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.payload.response.UserProfileDetails;
import com.system.vetcare.service.OwnerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerStrategyResolver implements UserProfileResolverStrategy {

    private final OwnerService ownerService;

    @Override
    public EAuthority getSupportedAuthority() {
        return OWNER;
    }

    @Override
    public UserProfileDetails resolveUserProfileDetails(Integer userId) {
        Owner owner = ownerService.findByUserId(userId);
        return new UserProfileDetails(owner.getId(), getSupportedAuthority().name());
    }

    @Override
    public void saveProfileForUser(User user) {
        ownerService.save(Owner
                            .builder()
                            .user(user)
                            .build());
    }
    
}