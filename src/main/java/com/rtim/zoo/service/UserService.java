package com.rtim.zoo.service;

import com.rtim.zoo.dto.PersonDto;

public interface UserService {

    PersonDto getByName(String name);
    
    void increaseFailedAttempts(PersonDto personDto);
    
    void resetFailedAttempts(String name);
    
    void lock(PersonDto personDto);
    
    boolean unlockWhenTimeExpired(PersonDto personDto);
    
    PersonDto registerNewPerson(PersonDto personDto);
    
}