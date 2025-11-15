package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.Set;

public interface LeaveService {

    Set<LocalDate> extractVeterinarianLeaveDays(Integer veterinarianId, LocalDate beginDate, LocalDate completeDate);

}