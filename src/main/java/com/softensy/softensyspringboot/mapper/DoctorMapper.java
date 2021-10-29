package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.DoctorDto;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.Price;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DoctorMapper {
    private final PriceRepository priceRepository;

    public DoctorDto entityToDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition().getPosition())
                .dateOfBirth(doctor.getDateOfBirth())
                .phoneNumber(doctor.getPhoneNumber())
                .build();
    }

    public Doctor dtoToEntity(DoctorDto doctor) {
        Price price = priceRepository.findById(doctor.getPosition())
                .orElseThrow(() -> new NotFoundException("Price not found"));
        return Doctor.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(price)
                .dateOfBirth(doctor.getDateOfBirth())
                .phoneNumber(doctor.getPhoneNumber())
                .build();
    }

}
