package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;

import java.time.LocalDateTime;
import java.util.Map;

public interface AppointmentService {

    AppointmentDto createAppointment(AppointmentDto appointmentDto);

    Map<LocalDateTime, PatientDto> getAllAppointmentsToDoctor(long doctorId);

    Map<LocalDateTime, DoctorDto> getAllPatientAppointments(long patientId);

    void deleteAppointment(long id);

}
