package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.PatientDto;
import com.softensy.softensyspringboot.entity.Patient;
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
