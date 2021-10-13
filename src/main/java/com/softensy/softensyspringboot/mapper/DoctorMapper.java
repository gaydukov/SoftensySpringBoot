package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.DoctorDto;
import com.softensy.softensyspringboot.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDto entityToDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfBirth(doctor.getDateOfBirth())
                .phoneNumber(doctor.getPhoneNumber())
                .build();
    }

    public Doctor dtoToEntity(DoctorDto doctor) {
        return Doctor.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfBirth(doctor.getDateOfBirth())
                .phoneNumber(doctor.getPhoneNumber())
                .build();
    }

}
