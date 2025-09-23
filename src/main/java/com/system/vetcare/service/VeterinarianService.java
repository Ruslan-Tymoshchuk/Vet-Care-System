package com.system.vetcare.service;

import java.util.List;
import com.system.vetcare.domain.Veterinarian;
import com.system.vetcare.payload.response.VeterinarianResponse;

public interface VeterinarianService {

    List<VeterinarianResponse> findAll();
    
    Veterinarian save(Veterinarian veterinarian);
    
    Veterinarian findByUserId(Integer id);

    Veterinarian findById(Integer id);

}