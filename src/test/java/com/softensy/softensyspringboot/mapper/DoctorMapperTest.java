package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.DoctorDto;
import com.softensy.softensyspringboot.entity.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.softensy.softensyspringboot.TestDataGenerator.getDoctorDto;
import static com.softensy.softensyspringboot.TestDataGenerator.getFirstDoctor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DoctorMapperTest {
    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    @DisplayName("check mapping doctor entity to dto")
    void testMappingDoctorEntityToDto() {
        // given
        Doctor doctor = getFirstDoctor();
        DoctorDto expectedDoctorDto = getDoctorDto(doctor);
        // when
        DoctorDto actualDoctorDto = doctorMapper.entityToDto(doctor);
        //then
        assertEquals(expectedDoctorDto.getId(), actualDoctorDto.getId());
        assertEquals(expectedDoctorDto.getFirstName(), actualDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), actualDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getMiddleName(), actualDoctorDto.getMiddleName());
        assertEquals(expectedDoctorDto.getPosition(), actualDoctorDto.getPosition());
        assertEquals(expectedDoctorDto.getDateOfBirth(), actualDoctorDto.getDateOfBirth());
        assertEquals(expectedDoctorDto.getPhoneNumber(), actualDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctorDto, actualDoctorDto);
        assertNotNull(expectedDoctorDto);
        assertNotNull(actualDoctorDto);
    }

    @Test
    @DisplayName("check mapping doctor dot to entity")
    void testMappingDoctorDtoToEntity() {
        // given
        Doctor expectedDoctor = getFirstDoctor();
        DoctorDto doctorDto = getDoctorDto(expectedDoctor);
        // when
        Doctor actualDoctor = doctorMapper.dtoToEntity(doctorDto);
        //then
        assertEquals(expectedDoctor.getId(), actualDoctor.getId());
        assertEquals(expectedDoctor.getFirstName(), actualDoctor.getFirstName());
        assertEquals(expectedDoctor.getLastName(), actualDoctor.getLastName());
        assertEquals(expectedDoctor.getMiddleName(), actualDoctor.getMiddleName());
        assertEquals(expectedDoctor.getPosition(), actualDoctor.getPosition());
        assertEquals(expectedDoctor.getDateOfBirth(), actualDoctor.getDateOfBirth());
        assertEquals(expectedDoctor.getPhoneNumber(), actualDoctor.getPhoneNumber());
        assertEquals(expectedDoctor, actualDoctor);
        assertNotNull(expectedDoctor);
        assertNotNull(actualDoctor);
    }

}
