package com.manager.animallist.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.manager.animallist.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>{
    
    boolean existsByNickName(String name);

    @Query(value = "SELECT P FROM Pet P WHERE P.owner.user.email = :userEmail")
    Set<Pet> findByUser(String userEmail);
    
}