package com.system.vetcare.service;

import com.system.vetcare.domain.Veterinarian;

public interface VeterinarianService {

    Veterinarian save(Veterinarian veterinarian);
    
    Veterinarian findByUserId(Integer id);

    Veterinarian findById(Integer id);

}