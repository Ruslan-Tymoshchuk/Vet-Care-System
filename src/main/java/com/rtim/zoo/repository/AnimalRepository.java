package com.rtim.zoo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rtim.zoo.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer>{

    List<Animal> findByPersonId(Integer id);
    
    boolean existsByNickName(String name);
    
}