package com.system.vetcare.service.impl;

import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Veterinarian;
import com.system.vetcare.repository.VeterinarianRepository;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianServiceImpl implements VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;

    @Override
    public Veterinarian save(Veterinarian veterinarian) {
        return veterinarianRepository.save(veterinarian);
    }

    @Override
    public Veterinarian findByUserId(Integer id) {
        return veterinarianRepository.findByUserId(id);
    }

}