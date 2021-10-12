package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.entity.Appointment;
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
        if(doctorRepository.findById(appointmentDto.getDoctorId()).isEmpty()){
            throw new NotFoundException("Doctor not found");
        }
        if (patientRepository.findById(appointmentDto.getPatientId()).isEmpty()){
            throw new NotFoundException("Patient not found");
        }
        return Appointment.builder()
                .doctor(doctorRepository.findById(appointmentDto.getDoctorId()).get())
                .patient(patientRepository.findById(appointmentDto.getPatientId()).get())
                .appointmentDate(appointmentDto.getAppointmentDate())
                .build();
    }

}
