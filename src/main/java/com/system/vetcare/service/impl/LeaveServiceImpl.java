package com.system.vetcare.service.impl;

import static java.util.stream.Collectors.toSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.system.vetcare.domain.Leave;
import com.system.vetcare.repository.LeaveRepository;
import com.system.vetcare.service.LeaveService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;

    @Override
    public Set<LocalDate> extractVeterinarianLeaveDays(Integer veterinarianId, LocalDate beginDate, LocalDate completeDate) {
        return extractDays(leaveRepository.findByVeterinarianInDateInterval(veterinarianId, beginDate, completeDate));
    }

    private Set<LocalDate> extractDays(List<Leave> leaves) {
        return leaves.stream().flatMap(leave -> leave.getBeginDate().datesUntil(leave.getCompleteDate().plusDays(1)))
                .collect(toSet());
    }
    
}