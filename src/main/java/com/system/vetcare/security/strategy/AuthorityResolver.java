package com.system.vetcare.security.strategy;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserAuthorityDetails;

@Service
public class AuthorityResolver {

    private final Map<EAuthority, AuthorityResolverStrategy> strategies;

    public AuthorityResolver(List<AuthorityResolverStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(toMap(AuthorityResolverStrategy::getSupportedAuthority, identity()));
    }

    public List<UserAuthorityDetails> resolveAllAuthorities(User user) {
        return user.getAuthorities().stream().map(authority -> {
            AuthorityResolverStrategy strategy = strategies.get(authority.getTitle());
            return strategy.resolveAuthorityDetails(user.getId());
        }).toList();
    }

}