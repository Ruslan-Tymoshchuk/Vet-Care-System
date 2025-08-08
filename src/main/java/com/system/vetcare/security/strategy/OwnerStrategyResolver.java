package com.system.vetcare.security.strategy;

import static com.system.vetcare.domain.enums.EAuthority.OWNER;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Owner;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserAuthorityDetails;
import com.system.vetcare.service.OwnerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerStrategyResolver implements AuthorityResolverStrategy {

    private final OwnerService ownerService;

    @Override
    public EAuthority getSupportedAuthority() {
        return OWNER;
    }

    @Override
    public UserAuthorityDetails resolveAuthorityDetails(Integer userId) {
        Owner owner = ownerService.findByUserId(userId);
        return new UserAuthorityDetails(owner.getId(), getSupportedAuthority().name());
    }

}