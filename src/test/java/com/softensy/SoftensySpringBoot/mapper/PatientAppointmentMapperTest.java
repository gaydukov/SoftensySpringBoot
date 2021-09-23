package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientAppointmentMapperTest {
    @MockBean
    private DoctorMapper doctorMapper;
    @Autowired
    private PatientAppointmentMapper patientAppointmentMapper;

    @Test
    @DisplayName("check mapping patient appointment entity to dto")
    void testMappingPatientAppointmentEntityToDto() {
        // given
        List<Appointment> appointmentList = getAppointmentList();
        List<PatientAppointmentDto> expectedAppointmentDtoList = getPatientAppointmentDtoList();
        List<DoctorDto> doctorDtoList = getDoctorDtoList();
        // when
        when(doctorMapper.entityToDto(getFirstDoctor())).thenReturn(doctorDtoList.get(0));
        when(doctorMapper.entityToDto(getSecondDoctor())).thenReturn(doctorDtoList.get(1));
        when(doctorMapper.entityToDto(getThirdDoctor())).thenReturn(doctorDtoList.get(2));
        List<PatientAppointmentDto> actualAppointmentDtoList = patientAppointmentMapper.entityToDto(appointmentList);
        //then
        for (int index = 0; index < expectedAppointmentDtoList.size(); index++) {
            assertEquals(expectedAppointmentDtoList.get(index).getDoctorDto()
                    , actualAppointmentDtoList.get(index).getDoctorDto());
            assertEquals(expectedAppointmentDtoList.get(index).getAppointmentDate()
                    , actualAppointmentDtoList.get(index).getAppointmentDate());
        }
        assertEquals(expectedAppointmentDtoList, actualAppointmentDtoList);
        assertNotNull(expectedAppointmentDtoList);
        assertNotNull(actualAppointmentDtoList);
        verify(doctorMapper, times(3)).entityToDto(any(Doctor.class));
    }

}
