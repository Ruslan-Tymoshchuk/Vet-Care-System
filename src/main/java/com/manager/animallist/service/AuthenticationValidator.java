package com.manager.animallist.service;

import com.manager.animallist.domain.DUser;
import com.manager.animallist.payload.AuthenticationRequest;

public interface AuthenticationValidator {

    void validateAuthenticationRequest(AuthenticationRequest authenticationRequest, DUser user);

}
