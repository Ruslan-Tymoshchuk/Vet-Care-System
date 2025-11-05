package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.List;
import com.system.vetcare.domain.Leave;

public interface LeavesService {

    List<Leave> findByStaffMemberAndDateInterval(Integer staffId, LocalDate start, LocalDate end);

}