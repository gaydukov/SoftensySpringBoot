package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.TestDataGenerator;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import com.softensy.SoftensySpringBoot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.getFirstPatient;
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
        boolean hasPatient = false;
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(patientSecurity.getLogin(), patientSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(patientSecurity));
        if (patient.getId() == patientSecurity.getUserId()) {
            hasPatient = patientSecurityService.hasPatient(patient);
        }
        //then
        assertTrue(hasPatient);
    }

}
