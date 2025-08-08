package com.system.vetcare.security.strategy;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserAuthorityDetails;

import lombok.Getter;

@Getter
@Service
public class AuthorityResolver {

    private final Map<EAuthority, AuthorityResolverStrategy> strategies;

    public AuthorityResolver(List<AuthorityResolverStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(toMap(AuthorityResolverStrategy::getSupportedAuthority, identity()));
    }

    public List<UserAuthorityDetails> resolveAllAuthorities(User user) {
        List<UserAuthorityDetails> userAuthorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> {
            AuthorityResolverStrategy strategy = strategies.get(authority.getTitle());
            UserAuthorityDetails userAuthorityDetails = strategy.resolveAuthorityDetails(user.getId());
            userAuthorities.add(userAuthorityDetails);
        });
        return userAuthorities;
    }

}