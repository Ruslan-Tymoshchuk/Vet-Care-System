package com.system.vetcare.security.payload;

public record UserAuthorityDetails(
        Integer userId, 
        String accessLevel) {
}