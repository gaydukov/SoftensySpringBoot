package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.PatientDto;
import com.softensy.softensyspringboot.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDto entityToDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfBirth(patient.getDateOfBirth())
                .doctorId(patient.getDoctorId())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }

    public Patient dtoToEntity(PatientDto patient) {
        return Patient.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfBirth(patient.getDateOfBirth())
                .doctorId(patient.getDoctorId())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }

}
