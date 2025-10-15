package com.system.vetcare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.system.vetcare.domain.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer>{

    Optional<Veterinarian> findByStaff_UserId(Integer id);

}