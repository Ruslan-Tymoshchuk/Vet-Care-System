package com.system.vetcare.service.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Leave;
import com.system.vetcare.repository.LeaveRepository;
import com.system.vetcare.service.LeavesService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeavesServiceImpl implements LeavesService {

    private final LeaveRepository leaveRepository;
    
    @Override
    public List<Leave> findByStaffMemberAndDateInterval(Integer staffId, LocalDate start, LocalDate end) {
        return leaveRepository.findAllInDateInterval(staffId, start, end);
    }

}