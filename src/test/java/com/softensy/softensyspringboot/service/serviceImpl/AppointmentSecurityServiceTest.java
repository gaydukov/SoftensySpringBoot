package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.TestDataGenerator;
import com.softensy.softensyspringboot.entity.Appointment;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.repository.AppointmentRepository;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstAppointment;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppointmentSecurityServiceTest {
    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private UserSecurityRepository userSecurityRepository;
    @Autowired
    private AppointmentSecurityService appointmentSecurityService;

    @Test
    @DisplayName("checking appointment contains authority patient")
    void testHasAuthorityPatientInAppointment() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        Appointment appointment = getFirstAppointment();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(appointment));
        boolean hasAppointmentAuthorityPatient = appointmentSecurityService.hasAuthorityPatientInAppointment(patientSecurity.getUserId());
        //then
        assertTrue(hasAppointmentAuthorityPatient);
    }

    @Test
    @DisplayName("checking appointment contains authority doctor")
    void testHasAuthorityDoctorInAppointment() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        Appointment appointment = getFirstAppointment();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(appointment));
        boolean hasAppointmentAuthorityDoctor = appointmentSecurityService.hasAuthorityDoctorInAppointment(doctorSecurity.getUserId());
        //then
        assertTrue(hasAppointmentAuthorityDoctor);
    }

}
