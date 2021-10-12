package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.entity.Appointment;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import com.softensy.softensyspringboot.repository.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentMapperTest {
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Test
    @DisplayName("check mapping appointment dto to entity")
    void testMappingAppointmentDtoToAppointmentEntity() {
        // given
        Appointment expectedAppointment = getFirstAppointment();
        expectedAppointment.setId(0);
        AppointmentDto appointmentDto = getAppointmentDto(expectedAppointment);
        Doctor doctor = getFirstDoctor();
        Patient patient = getFirstPatient();
        // when
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        Appointment actualAppointment = appointmentMapper.dtoToEntity(appointmentDto);
        //then
        assertEquals(expectedAppointment.getId(), actualAppointment.getId());
        assertEquals(expectedAppointment.getDoctor(), actualAppointment.getDoctor());
        assertEquals(expectedAppointment.getPatient(), actualAppointment.getPatient());
        assertEquals(expectedAppointment.getAppointmentDate(), actualAppointment.getAppointmentDate());
        assertEquals(expectedAppointment, actualAppointment);
        assertNotNull(expectedAppointment);
        assertNotNull(actualAppointment);
        verify(doctorRepository, times(2)).findById(anyLong());
        verify(patientRepository, times(2)).findById(anyLong());
    }

}
