package com.system.vetcare.security.strategy;

import static com.system.vetcare.domain.enums.EAuthority.VETERINARIAN;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.Veterinarian;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserProfileDetails;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianStrategyResolver implements UserProfileResolverStrategy {

    private final VeterinarianService veterinarianService;

    @Override
    public EAuthority getSupportedAuthority() {
        return VETERINARIAN;
    }

    @Override
    public UserProfileDetails resolveUserProfileDetails(Integer userId) {
        Veterinarian veterinarian = veterinarianService.findByUserId(userId);
        return new UserProfileDetails(veterinarian.getId(), getSupportedAuthority().name());
    }

    @Override
    public void saveProfileForUser(User user) {
        veterinarianService.save(Veterinarian
                                   .builder()
                                   .user(user)
                                   .build());
    }

}