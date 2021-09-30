package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.TestDataGenerator;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import com.softensy.SoftensySpringBoot.mapper.UserSecurityMapper;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private UserSecurityMapper userSecurityMapper;
    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private UserSecurityRepository userSecurityRepository;

    @Test
    @DisplayName("checking loud userDetails by userName")
    void testLoadUserDetailsByUserName() {
        // given
        UserDetails expectedUserDetails = getUserDetails();
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        when(userSecurityMapper.userSecurityToUserDetails(any(UserSecurity.class))).thenReturn(expectedUserDetails);
        UserDetails actualDoctor = userDetailsService.loadUserByUsername(patientSecurity.getLogin());
        //then
        assertEquals(expectedUserDetails, actualDoctor);
        assertNotNull(expectedUserDetails);
        verify(userSecurityRepository, times(1)).findByLogin(anyString());
        verify(userSecurityMapper, times(1)).userSecurityToUserDetails(any(UserSecurity.class));
    }

    @Test
    @DisplayName("checking doctor contains required id")
    void testDoctorContainsRequiredId() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        SecurityContextHolder.getContext().
                setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        boolean hasDoctorId = userDetailsService.hasDoctorId(doctorSecurity.getUserId());
        //then
        assertTrue(hasDoctorId);
    }

    @Test
    @DisplayName("required doctor check")
    void testHasRequiredDoctor() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        Doctor doctor = getFirstDoctor();
        boolean hasDoctor = false;
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        if (doctor.getId() == doctorSecurity.getUserId()) {
            hasDoctor = userDetailsService.hasDoctor(doctor);
        }
        //then
        assertTrue(hasDoctor);
    }

    @Test
    @DisplayName("checking patient contains required id")
    void testPatientContainsRequiredId() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        boolean hasPatientId = userDetailsService.hasPatientId(patientSecurity.getUserId());
        //then
        assertTrue(hasPatientId);
    }

    @Test
    @DisplayName("required patient check ")
    void testHasRequiredPatient() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        Patient patient = getFirstPatient();
        boolean hasPatient = false;
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        if (patient.getId() == patientSecurity.getUserId()) {
            hasPatient = userDetailsService.hasPatient(patient);
        }
        //then
        assertTrue(hasPatient);
    }

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
        boolean hasAppointmentAuthorityPatient = userDetailsService.hasAuthorityPatientInAppointment(patientSecurity.getUserId());
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
        boolean hasAppointmentAuthorityDoctor = userDetailsService.hasAuthorityDoctorInAppointment(doctorSecurity.getUserId());
        //then
        assertTrue(hasAppointmentAuthorityDoctor);
    }

}
