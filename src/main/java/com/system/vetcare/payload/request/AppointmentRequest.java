package com.system.vetcare.payload.request;

public record AppointmentRequest(
		Integer id,
		Integer ownerId,
		Integer petId,
		Integer veterinarianId,
		String visitDate,
		Integer timeSlotId,
		String room,
		String reason) {
}