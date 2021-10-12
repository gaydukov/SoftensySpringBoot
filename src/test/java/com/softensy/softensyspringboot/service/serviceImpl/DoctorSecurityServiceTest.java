package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.TestDataGenerator;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstDoctor;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorSecurityServiceTest {
    @MockBean
    private UserSecurityRepository userSecurityRepository;
    @Autowired
    private DoctorSecurityService doctorSecurityService;

    @Test
    @DisplayName("checking doctor contains required id")
    void testDoctorContainsRequiredId() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        SecurityContextHolder.getContext().
                setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        boolean hasDoctorId = doctorSecurityService.hasDoctorId(doctorSecurity.getUserId());
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
            hasDoctor = doctorSecurityService.hasDoctor(doctor);
        }
        //then
        assertTrue(hasDoctor);
    }

}
