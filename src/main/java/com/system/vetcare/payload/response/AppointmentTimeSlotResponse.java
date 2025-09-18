package com.system.vetcare.payload.response;

public record AppointmentTimeSlotResponse(
		Integer id,
		String startTime,
		String endTime) {
}