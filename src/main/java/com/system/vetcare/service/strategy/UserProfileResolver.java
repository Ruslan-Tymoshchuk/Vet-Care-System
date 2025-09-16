package com.system.vetcare.service.strategy;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.payload.response.UserProfileDetails;

@Service
public class UserProfileResolver {

    private final Map<EAuthority, UserProfileResolverStrategy> strategies;

    public UserProfileResolver(List<UserProfileResolverStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(toMap(UserProfileResolverStrategy::getSupportedAuthority, identity()));
    }

    public List<UserProfileDetails> resolveUserProfiles(User user) {
        return user.getAuthorities().stream().map(authority -> {
            UserProfileResolverStrategy strategy = strategies.get(EAuthority.valueOf(authority.getAuthority()));
            return strategy.resolveUserProfileDetails(user.getId());
        }).toList();
    }

    public void saveUserProfiles(User user) {
        user.getAuthorities().forEach(authority -> {
            UserProfileResolverStrategy strategy = strategies.get(EAuthority.valueOf(authority.getAuthority()));
            strategy.saveProfileForUser(user);
        });
    }

}