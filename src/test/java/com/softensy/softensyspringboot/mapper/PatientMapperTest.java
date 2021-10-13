package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.PatientDto;
import com.softensy.softensyspringboot.entity.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstPatient;
import static com.softensy.softensyspringboot.TestDataGenerator.getPatientDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PatientMapperTest {
    @Autowired
    private PatientMapper patientMapper;

    @Test
    @DisplayName("check mapping patient entity to dto")
    void testMappingPatientEntityToDto() {
        // given
        Patient patient = getFirstPatient();
        PatientDto expectedPatientDto = getPatientDto(patient);
        // when
        PatientDto actualPatientDto = patientMapper.entityToDto(patient);
        //then
        assertEquals(expectedPatientDto.getId(), actualPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), actualPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), actualPatientDto.getLastName());
        assertEquals(expectedPatientDto.getMiddleName(), actualPatientDto.getMiddleName());
        assertEquals(expectedPatientDto.getDateOfBirth(), actualPatientDto.getDateOfBirth());
        assertEquals(expectedPatientDto.getPhoneNumber(), actualPatientDto.getPhoneNumber());
        assertEquals(expectedPatientDto.getDoctorId(), actualPatientDto.getDoctorId());
        assertEquals(expectedPatientDto, actualPatientDto);
        assertNotNull(expectedPatientDto);
        assertNotNull(actualPatientDto);
    }

    @Test
    @DisplayName("check mapping patient dto to entity")
    void testMappingPatientDtoToEntity() {
        // given
        Patient expectedPatient = getFirstPatient();
        PatientDto patientDto = getPatientDto(expectedPatient);
        // when
        Patient actualPatient = patientMapper.dtoToEntity(patientDto);
        //then
        assertEquals(expectedPatient.getId(), actualPatient.getId());
        assertEquals(expectedPatient.getFirstName(), actualPatient.getFirstName());
        assertEquals(expectedPatient.getLastName(), actualPatient.getLastName());
        assertEquals(expectedPatient.getMiddleName(), actualPatient.getMiddleName());
        assertEquals(expectedPatient.getDateOfBirth(), actualPatient.getDateOfBirth());
        assertEquals(expectedPatient.getPhoneNumber(), actualPatient.getPhoneNumber());
        assertEquals(expectedPatient.getDoctorId(), actualPatient.getDoctorId());
        assertEquals(expectedPatient, actualPatient);
        assertNotNull(expectedPatient);
        assertNotNull(actualPatient);
    }

}
