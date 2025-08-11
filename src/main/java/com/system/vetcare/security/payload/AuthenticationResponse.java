package com.system.vetcare.security.payload;

import java.util.*;

public record AuthenticationResponse(
        String email, 
        String lastLogin,
        List<UserAuthorityDetails> userAuthorities) {
}