package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.softensy.SoftensySpringBoot.TestDataGeneration.getFirstPatient;
import static com.softensy.SoftensySpringBoot.TestDataGeneration.getPatientDto;
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
        assertEquals(expectedPatientDto, actualPatientDto);
        assertNotNull(expectedPatientDto);
        assertNotNull(actualPatientDto);
    }

}
