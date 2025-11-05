package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import com.system.vetcare.domain.Leave;

public interface LeaveService {

    List<Leave> findByStaffMemberAndDateInterval(Integer staffId, LocalDate start, LocalDate end);

    Set<LocalDate> extractAbsentDays(List<Leave> leaves);

}