package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment);

    List<PatientDto> getAllAppointmentsToDoctor(long doctorId);

    List<DoctorDto> getAllPatientAppointments(long patientId);

    void deleteAppointment(long id);

}
