package com.system.vetcare.service;

import java.time.LocalDate;
import java.util.Set;

public interface LeaveService {

    Set<LocalDate> extractAbsentDays(Integer staffId, LocalDate beginDate, LocalDate completeDate);

}