package com.system.vetcare.security.payload;

public record UserProfileDetails(
        Integer profileId, 
        String accessLevel) {
}