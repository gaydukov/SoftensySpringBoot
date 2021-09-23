package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AppointmentMapper {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public Appointment dtoToEntity(AppointmentDto appointmentDto) {
        return Appointment.builder()
                .doctor(doctorRepository.findById(appointmentDto.getDoctorId()).get())
                .patient(patientRepository.findById(appointmentDto.getPatientId()).get())
                .appointmentDate(appointmentDto.getAppointmentDate())
                .build();
    }

}
