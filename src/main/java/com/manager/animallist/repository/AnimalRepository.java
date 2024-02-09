package com.manager.animallist.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.manager.animallist.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer>{
    
    boolean existsByNickName(String name);

    @Query(value = "SELECT A FROM Animal A WHERE A.user.email = :userEmail")
    Set<Animal> findByUser(String userEmail);
    
}