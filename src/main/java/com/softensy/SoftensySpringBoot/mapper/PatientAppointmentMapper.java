package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PatientAppointmentMapper {
    private final DoctorMapper doctorMapper;

    public List<PatientAppointmentDto> entityToDto(List<Appointment> appointmentList) {
        PatientAppointmentDto patientAppointmentDto;
        List<PatientAppointmentDto> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            patientAppointmentDto = PatientAppointmentDto.builder()
                    .doctorDto(doctorMapper.entityToDto(appointment.getDoctor()))
                    .appointmentDate(appointment.getAppointmentDate())
                    .build();
            result.add(patientAppointmentDto);
        }
        return result;
    }

}
