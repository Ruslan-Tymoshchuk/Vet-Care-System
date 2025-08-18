package com.system.vetcare.service.impl;

import static java.lang.String.format;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Veterinarian;
import com.system.vetcare.exception.EntityNotFoundException;
import com.system.vetcare.repository.VeterinarianRepository;
import com.system.vetcare.service.VeterinarianService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianServiceImpl implements VeterinarianService {

    public static final String VETERINARIAN_WITH_USER_ID_NOT_FOUND = "Veterinarian with [user id: %s] not found.";
    public static final String VETERINARIAN_WITH_ID_NOT_FOUND = "Veterinarian with [id: %s] not found.";

    private final VeterinarianRepository veterinarianRepository;

    @Override
    public Veterinarian save(Veterinarian veterinarian) {
        return veterinarianRepository.save(veterinarian);
    }

    @Override
    public Veterinarian findByUserId(Integer id) {
        return veterinarianRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(format(VETERINARIAN_WITH_USER_ID_NOT_FOUND, id)));
    }

    @Override
    public Veterinarian findById(Integer id) {
        return veterinarianRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(VETERINARIAN_WITH_ID_NOT_FOUND, id)));
    }

}