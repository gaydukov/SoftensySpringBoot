package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.DoctorDto;
import com.softensy.softensyspringboot.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDto entityToDto(Doctor doctor) {
        return DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
    }

}
