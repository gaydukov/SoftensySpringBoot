package com.softensy.softensyspringboot.service;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.dto.DoctorAppointmentDto;
import com.softensy.softensyspringboot.dto.PatientAppointmentDto;

import java.util.List;

public interface AppointmentService {

    AppointmentDto createAppointment(AppointmentDto appointmentDto);

    List<DoctorAppointmentDto> getAllDoctorAppointments(long doctorId);

    List<PatientAppointmentDto> getAllPatientAppointments(long patientId);

    void deleteAppointment(long id);

}
