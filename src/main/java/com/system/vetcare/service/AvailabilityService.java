package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.List;
import com.system.vetcare.payload.response.AvailableDateResponse;

public interface AvailabilityService {

    List<AvailableDateResponse> calculateVeterinarianAvailabilitySlots(Integer id, LocalDate beginDate,
            LocalDate completeDate);

}