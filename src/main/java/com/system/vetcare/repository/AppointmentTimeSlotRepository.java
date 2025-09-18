package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.vetcare.domain.AppointmentTimeSlot;

public interface AppointmentTimeSlotRepository extends JpaRepository<AppointmentTimeSlot, Integer> {}