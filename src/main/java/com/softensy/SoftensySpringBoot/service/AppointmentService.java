package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;

import java.util.List;

public interface AppointmentService {

    AppointmentDto createAppointment(AppointmentDto appointmentDto);

    List<DoctorAppointmentDto> getAllDoctorAppointments(long doctorId);

    List<PatientAppointmentDto> getAllPatientAppointments(long patientId);

    void deleteAppointment(long id);

}
