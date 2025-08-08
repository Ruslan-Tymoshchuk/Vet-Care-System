package com.system.vetcare.service;

import com.system.vetcare.domain.Veterinarian;

public interface VeterinarianService {

    Veterinarian findByUserId(Integer id);

}
