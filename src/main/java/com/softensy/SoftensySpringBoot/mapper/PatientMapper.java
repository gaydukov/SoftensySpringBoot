package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDto entityToDto(Patient patient) {
        return PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
    }

}
