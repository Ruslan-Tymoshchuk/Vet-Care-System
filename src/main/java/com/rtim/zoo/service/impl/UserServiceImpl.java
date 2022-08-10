package com.rtim.zoo.service.impl;

import static java.time.LocalDateTime.now;
import static java.time.Duration.between;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rtim.zoo.domain.Person;
import com.rtim.zoo.dto.MapstractMapper;
import com.rtim.zoo.dto.PersonDto;
import com.rtim.zoo.exception.UsernameAlredyExistsException;
import com.rtim.zoo.repository.PeopleRepository;
import com.rtim.zoo.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Value("${lock.time.duration}")
    private int lockTimeDuration;
    private final PeopleRepository peopleRepository;
    private final MapstractMapper personMapper;
    private final PasswordEncoder passwordEncoder;
   
    public UserServiceImpl(PeopleRepository peopleRepository, MapstractMapper personMapper, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    public PersonDto getByName(String name) throws UsernameNotFoundException {
        return personMapper.personToPersonDto(peopleRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Transactional
    public void increaseFailedAttempts(PersonDto personDto) {
        int newFailAttempts = personDto.getFailedAttempt() + 1;
        peopleRepository.updateFailedAttempts(newFailAttempts, personDto.getName());
    }
     
    @Transactional
    public void resetFailedAttempts(String name) {
        peopleRepository.updateFailedAttempts(0, name);
    }
     
    @Transactional
    public void lock(PersonDto personDto) {
        personDto.setAccountNonLocked(false);
        personDto.setLockTime(now());
        peopleRepository.save(personMapper.personDtoToPerson(personDto));
    }

    @Transactional
    public boolean unlockWhenTimeExpired(PersonDto personDto) {
        if (personDto.getLockTime() == null) {
            return true;
        } else if (between(personDto.getLockTime(), now()).toSeconds() >= lockTimeDuration) {
            personDto.setAccountNonLocked(true);
            personDto.setLockTime(null);
            personDto.setFailedAttempt(0);
            peopleRepository.save(personMapper.personDtoToPerson(personDto));
            return true;
        } else {
            return false;
        }
    }
             
    @Transactional
    public PersonDto registerNewPerson(PersonDto personDto) {
        if (!peopleRepository.existsByName(personDto.getName())) {
            Person person = personMapper.personDtoToPerson(personDto);
            person.setPassword(passwordEncoder.encode(personDto.getPassword()));
            return personMapper.personToPersonDto(peopleRepository.save(person));
        } else {
            throw new UsernameAlredyExistsException("User with that name already exists");
        }
    }
}