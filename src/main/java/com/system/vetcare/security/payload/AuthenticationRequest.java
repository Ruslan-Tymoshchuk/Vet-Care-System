package com.system.vetcare.security.payload;

public record AuthenticationRequest(
        String email,
        String password) {
}