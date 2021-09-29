package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.TestDataGenerator;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.DoctorSecurity;
import com.softensy.SoftensySpringBoot.entity.PatientSecurity;
import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import com.softensy.SoftensySpringBoot.mapper.UserSecurityMapper;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.repository.DoctorSecurityRepository;
import com.softensy.SoftensySpringBoot.repository.PatientSecurityRepository;
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
    private PatientSecurityRepository patientSecurityRepository;
    @MockBean
    private DoctorSecurityRepository doctorSecurityRepository;
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("checking loud userDetails by userName")
    void testLoadUserDetailsByUserName() {
        // given
        UserSecurity userSecurity = getUserSecurity();
        UserDetails expectedUserDetails = getUserDetails();
        PatientSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        // when
        when(patientSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        when(userSecurityMapper.userSecurityToUserDetails(any(UserSecurity.class))).thenReturn(expectedUserDetails);
        UserDetails actualDoctor = userDetailsService.loadUserByUsername(userSecurity.getLogin());
        //then
        assertEquals(expectedUserDetails, actualDoctor);
        assertNotNull(expectedUserDetails);
        verify(patientSecurityRepository, times(2)).findByLogin(anyString());
        verify(userSecurityMapper, times(1)).userSecurityToUserDetails(any(UserSecurity.class));
    }

    @Test
    @DisplayName("checking get doctorSecurity by doctor login")
    void testGetDoctorSecurityByLogin() {
        // given
        DoctorSecurity expectedDoctorSecurity = TestDataGenerator.getDoctorSecurity();
        // when
        when(doctorSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(expectedDoctorSecurity));
        DoctorSecurity actualDoctorSecurity = userDetailsService.getDoctorSecurity(expectedDoctorSecurity.getLogin()).get();
        //then
        assertEquals(expectedDoctorSecurity, actualDoctorSecurity);
        assertNotNull(expectedDoctorSecurity);
    }

    @Test
    @DisplayName("checking get patientSecurity by patient login")
    void testGetPatientSecurityByLogin() {
        // given
        PatientSecurity expectedPatientSecurity = TestDataGenerator.getPatientSecurity();
        // when
        when(patientSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(expectedPatientSecurity));
        PatientSecurity actualPatientSecurity = userDetailsService.getPatientSecurity(expectedPatientSecurity.getLogin()).get();
        //then
        assertEquals(expectedPatientSecurity, actualPatientSecurity);
        assertNotNull(expectedPatientSecurity);
    }

    @Test
    @DisplayName("checking doctor contains required id")
    void testDoctorContainsRequiredId() {
        // given
        DoctorSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        SecurityContextHolder.getContext().
                setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(doctorSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        boolean hasDoctorId = userDetailsService.hasDoctorId(doctorSecurity.getId());
        //then
        assertTrue(hasDoctorId);
    }

    @Test
    @DisplayName("required doctor check")
    void testHasRequiredDoctor() {
        // given
        DoctorSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(doctorSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        boolean hasDoctor = userDetailsService.hasDoctor(doctorSecurity.getDoctor());
        //then
        assertTrue(hasDoctor);
    }

    @Test
    @DisplayName("checking patient contains required id")
    void testPatientContainsRequiredId() {
        // given
        PatientSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(patientSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        boolean hasPatientId = userDetailsService.hasPatientId(patientSecurity.getId());
        //then
        assertTrue(hasPatientId);
    }

    @Test
    @DisplayName("required patient check ")
    void testHasRequiredPatient() {
        // given
        PatientSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(patientSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        boolean hasPatient = userDetailsService.hasPatient(patientSecurity.getPatient());
        //then
        assertTrue(hasPatient);
    }

    @Test
    @DisplayName("checking appointment contains authority patient")
    void testHasAuthorityPatientInAppointment() {
        // given
        PatientSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        Appointment appointment = getFirstAppointment();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(patientSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(appointment));
        boolean hasAppointmentAuthorityPatient = userDetailsService.hasAuthorityPatientInAppointment(patientSecurity.getPatient().getId());
        //then
        assertTrue(hasAppointmentAuthorityPatient);
    }

    @Test
    @DisplayName("checking appointment contains authority doctor")
    void testHasAuthorityDoctorInAppointment() {
        // given
        DoctorSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        Appointment appointment = getFirstAppointment();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(doctorSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(appointment));
        boolean hasAppointmentAuthorityDoctor = userDetailsService.hasAuthorityDoctorInAppointment(doctorSecurity.getDoctor().getId());
        //then
        assertTrue(hasAppointmentAuthorityDoctor);
    }

}
