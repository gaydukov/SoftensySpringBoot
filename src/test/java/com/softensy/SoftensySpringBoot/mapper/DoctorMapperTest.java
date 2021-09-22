package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.getDoctorDto;
import static com.softensy.SoftensySpringBoot.TestDataGenerator.getFirstDoctor;
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
        assertEquals(expectedDoctorDto.getFirstName(), actualDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), actualDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getMiddleName(), actualDoctorDto.getMiddleName());
        assertEquals(expectedDoctorDto.getPosition(), actualDoctorDto.getPosition());
        assertEquals(expectedDoctorDto, actualDoctorDto);
        assertNotNull(expectedDoctorDto);
        assertNotNull(actualDoctorDto);
    }

}
