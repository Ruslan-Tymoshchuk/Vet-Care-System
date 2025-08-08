package com.system.vetcare.security.strategy;

import static com.system.vetcare.domain.enums.EAuthority.VETERINARIAN;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Veterinarian;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserAuthorityDetails;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianStrategyResolver implements AuthorityResolverStrategy {

    private final VeterinarianService veterinarianService;

    @Override
    public EAuthority getSupportedAuthority() {
        return VETERINARIAN;
    }
    
    @Override
    public UserAuthorityDetails resolveAuthorityDetails(Integer userId) {
        Veterinarian veterinarian = veterinarianService.findByUserId(userId);
        return new UserAuthorityDetails(veterinarian.getId(), getSupportedAuthority().name());
    }
    
}