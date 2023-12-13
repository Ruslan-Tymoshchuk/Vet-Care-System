package com.manager.animallist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manager.animallist.domain.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{
    
    Optional<Person> findByName(String name);
    
    boolean existsByName(String name);
           
    @Modifying
    @Query("UPDATE Person p SET p.failedAttempt = :failAttempts WHERE p.name = :name")
    public void updateFailedAttempts(@Param("failAttempts") int failAttempts, @Param("name") String name);
    
}