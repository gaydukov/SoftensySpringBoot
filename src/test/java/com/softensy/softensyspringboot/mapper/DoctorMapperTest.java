package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.DoctorDto;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.Price;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorMapperTest {
    @Autowired
    private DoctorMapper doctorMapper;
    @MockBean
    private PriceRepository priceRepository;

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
    @DisplayName("check mapping doctor dto to entity")
    void testMappingDoctorDtoToEntity() {
        // given
        Doctor expectedDoctor = getFirstDoctor();
        DoctorDto doctorDto = getDoctorDto(expectedDoctor);
        Price price = getFirstPrice();
        // when
        when(priceRepository.findById(anyString())).thenReturn(Optional.ofNullable(price));
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

    @Test
    @DisplayName("check mapping doctor dto to entity with empty price, expected exception")
    void testMappingDoctorDtoToEntityWithEmptyPrice() {
        // given
        DoctorDto doctor = getDoctorDto(getFirstDoctor());
        // when
        when(priceRepository.findById(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> doctorMapper.dtoToEntity(doctor));

    }

}
