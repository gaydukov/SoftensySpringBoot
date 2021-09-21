package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DoctorAppointmentMapper {
    private final PatientMapper patientMapper;

    public List<DoctorAppointmentDto> entityToDto(List<Appointment> appointmentList) {
        DoctorAppointmentDto doctorAppointmentDto;
        List<DoctorAppointmentDto> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            doctorAppointmentDto = DoctorAppointmentDto.builder()
                    .patientDto(patientMapper.entityToDto(appointment.getPatient()))
                    .appointmentDate(appointment.getAppointmentDate())
                    .build();
            result.add(doctorAppointmentDto);
        }
        return result;
    }

}
