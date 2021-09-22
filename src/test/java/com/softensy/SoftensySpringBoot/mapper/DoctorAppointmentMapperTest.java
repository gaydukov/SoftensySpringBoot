package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorAppointmentMapperTest {
    @MockBean
    private PatientMapper patientMapper;
    @Autowired
    private DoctorAppointmentMapper doctorAppointmentMapper;

    @Test
    @DisplayName("check mapping doctor appointment entity to dto")
    void testMappingDoctorAppointmentEntityToDto() {
        // given
        List<Appointment> appointmentList = getAppointmentList();
        List<DoctorAppointmentDto> expectedAppointmentDtoList = getDoctorAppointmentDtoList();
        List<PatientDto> patientDtoList = getPatientDtoList();
        // when
        when(patientMapper.entityToDto(getFirstPatient())).thenReturn(patientDtoList.get(0));
        when(patientMapper.entityToDto(getSecondPatient())).thenReturn(patientDtoList.get(1));
        when(patientMapper.entityToDto(getThirdPatient())).thenReturn(patientDtoList.get(2));
        List<DoctorAppointmentDto> actualAppointmentDtoList = doctorAppointmentMapper.entityToDto(appointmentList);
        //then
        for (int index = 0; index < expectedAppointmentDtoList.size(); index++) {
            assertEquals(expectedAppointmentDtoList.get(index).getPatientDto(), actualAppointmentDtoList.get(index).getPatientDto());
            assertEquals(expectedAppointmentDtoList.get(index).getAppointmentDate(), actualAppointmentDtoList.get(index).getAppointmentDate());
        }
        assertEquals(expectedAppointmentDtoList, actualAppointmentDtoList);
        assertNotNull(expectedAppointmentDtoList);
        assertNotNull(actualAppointmentDtoList);
        verify(patientMapper, times(3)).entityToDto(any(Patient.class));
    }

}
