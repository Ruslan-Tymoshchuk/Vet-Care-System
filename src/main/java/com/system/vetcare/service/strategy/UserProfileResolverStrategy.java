package com.system.vetcare.service.strategy;

import com.system.vetcare.domain.User;
import com.system.vetcare.domain.enums.EAuthority;
import com.system.vetcare.payload.response.UserProfileDetails;

public interface UserProfileResolverStrategy {

    EAuthority getSupportedAuthority();
    
    UserProfileDetails resolveUserProfileDetails(Integer userId);

    void saveProfileForUser(User user);
    
}