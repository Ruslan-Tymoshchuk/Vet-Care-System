package com.system.vetcare.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.system.vetcare.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>{
    
    boolean existsByNickName(String name);

    @Query(value = "SELECT P FROM Pet P WHERE P.owner.id = :ownerId")
    Set<Pet> findByOwner(Integer ownerId);

    @Query(value = "SELECT P FROM Pet P WHERE P.veterinarian.id = :veterinarianId")
    Set<Pet> findByVeterinarian(Integer veterinarianId);
    
}