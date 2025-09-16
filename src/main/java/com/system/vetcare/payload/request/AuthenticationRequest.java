package com.system.vetcare.payload.request;

public record AuthenticationRequest(
        String email,
        String password) {
}