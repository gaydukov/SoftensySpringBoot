package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.entity.Appointment;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import com.softensy.softensyspringboot.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AppointmentMapper {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public Appointment dtoToEntity(AppointmentDto appointmentDto) {
        Doctor doctor = doctorRepository.findById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(appointmentDto.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found"));
        return Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmentDate(appointmentDto.getAppointmentDate())
                .build();
    }

}
