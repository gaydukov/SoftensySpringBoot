package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.TestDataGenerator;
import com.softensy.softensyspringboot.dto.PatientDto;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstPatient;
import static com.softensy.softensyspringboot.TestDataGenerator.getPatientDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientSecurityServiceTest {
    @MockBean
    private UserSecurityRepository userSecurityRepository;
    @Autowired
    private PatientSecurityService patientSecurityService;

    @Test
    @DisplayName("checking patient contains required id by invalid userSecurity login")
    void testPatientContainsWithInvalidUserLogin() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        long patientId = patientSecurity.getUserId();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenThrow(UsernameNotFoundException.class);
        //then
        assertThrows(UsernameNotFoundException.class, () -> patientSecurityService.hasPatientId(patientId));
    }

    @Test
    @DisplayName("checking patient contains required id")
    void testPatientContainsRequiredId() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        boolean hasPatientId = patientSecurityService.hasPatientId(patientSecurity.getUserId());
        //then
        assertTrue(hasPatientId);
    }

    @Test
    @DisplayName("required patient check ")
    void testHasRequiredPatient() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        Patient patient = getFirstPatient();
        PatientDto patientDto = getPatientDto(getFirstPatient());
        boolean hasPatient = false;
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        if (patient.getId() == patientSecurity.getUserId()) {
            hasPatient = patientSecurityService.hasPatient(patientDto);
        }
        //then
        assertTrue(hasPatient);
    }

    @Test
    @DisplayName("required patient check by invalid userSecurity login ")
    void testHasRequiredPatientWithInvalidLogin() {
        // given
        UserSecurity patientSecurity = TestDataGenerator.getPatientSecurity();
        PatientDto patientDto = getPatientDto(getFirstPatient());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenThrow(UsernameNotFoundException.class);
        //then
        assertThrows(UsernameNotFoundException.class, () -> patientSecurityService.hasPatient(patientDto));
    }

}
