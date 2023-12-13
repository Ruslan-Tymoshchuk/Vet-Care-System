package com.manager.animallist.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.manager.animallist.domain.Gender;

public class AnimalDto {

    private Integer id;
    private LocalDate birthDate;
    private Gender gender;
    private String nickName;
    private Integer userId;
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, gender, id, nickName, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnimalDto other = (AnimalDto) obj;
        return Objects.equals(birthDate, other.birthDate) && gender == other.gender && Objects.equals(id, other.id)
                && Objects.equals(nickName, other.nickName) && Objects.equals(userId, other.userId);
    }
}