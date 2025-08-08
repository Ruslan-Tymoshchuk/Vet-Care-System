package com.system.vetcare.security.strategy;

import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.security.payload.UserAuthorityDetails;

public interface AuthorityResolverStrategy {

    EAuthority getSupportedAuthority();
    
    UserAuthorityDetails resolveAuthorityDetails(Integer userId);
    
}