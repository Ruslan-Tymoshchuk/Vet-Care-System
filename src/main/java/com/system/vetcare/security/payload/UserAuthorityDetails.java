package com.system.vetcare.security.payload;

public record UserAuthorityDetails(
        Integer profileId, 
        String accessLevel) {
}