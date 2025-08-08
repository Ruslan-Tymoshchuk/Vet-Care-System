package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.vetcare.domain.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer>{

    Veterinarian findByUserId(Integer id);

}