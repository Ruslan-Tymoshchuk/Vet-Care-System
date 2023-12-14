package com.manager.animallist.service;

import com.manager.animallist.payload.PersonDto;

public interface UserService {

    PersonDto getByName(String name);
    
    void increaseFailedAttempts(PersonDto personDto);
    
    void resetFailedAttempts(String name);
    
    void lock(PersonDto personDto);
    
    boolean unlockWhenTimeExpired(PersonDto personDto);
    
    PersonDto registerNewPerson(PersonDto personDto);
    
}