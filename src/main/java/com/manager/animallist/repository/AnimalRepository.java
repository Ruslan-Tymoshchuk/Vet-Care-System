package com.manager.animallist.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.animallist.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer>{

    List<Animal> findByPersonId(Integer id);
    
    boolean existsByNickName(String name);
    
}