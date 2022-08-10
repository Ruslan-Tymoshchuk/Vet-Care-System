package com.rtim.zoo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PersonDto {

    private Integer id;
    private String name;
    private String password;
    private boolean accountNonLocked;
    private int failedAttempt;
    private LocalDateTime lockTime;
    private List<AnimalDto> animals;
      
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public List<AnimalDto> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalDto> animals) {
        this.animals = animals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNonLocked, failedAttempt, id, lockTime, name, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonDto other = (PersonDto) obj;
        return accountNonLocked == other.accountNonLocked && failedAttempt == other.failedAttempt
                && Objects.equals(id, other.id) && Objects.equals(lockTime, other.lockTime)
                && Objects.equals(name, other.name) && Objects.equals(password, other.password);
    }
}