package com.system.vetcare.security.strategy;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserProfileDetails;

@Service
public class UserProfileResolver {

    private final Map<EAuthority, UserProfileResolverStrategy> strategies;

    public UserProfileResolver(List<UserProfileResolverStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(toMap(UserProfileResolverStrategy::getSupportedAuthority, identity()));
    }

    public List<UserProfileDetails> resolveUserProfiles(User user) {
        return user.getAuthorities().stream().map(authority -> {
            UserProfileResolverStrategy strategy = strategies.get(authority.getTitle());
            return strategy.resolveUserProfileDetails(user.getId());
        }).toList();
    }

    public void saveUserProfiles(User user) {
        user.getAuthorities().forEach(authority -> {
            UserProfileResolverStrategy strategy = strategies.get(authority.getTitle());
            strategy.saveProfileForUser(user);
        });
    }

}