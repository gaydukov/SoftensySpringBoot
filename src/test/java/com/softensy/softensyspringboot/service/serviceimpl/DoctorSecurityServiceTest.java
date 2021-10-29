package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.TestDataGenerator;
import com.softensy.softensyspringboot.dto.DoctorDto;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.getDoctorDto;
import static com.softensy.softensyspringboot.TestDataGenerator.getFirstDoctor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    @DisplayName("checking doctor contains id by invalid userSecurity login")
    void testDoctorHasIdWithInvalidUserLogin() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        long doctorId = doctorSecurity.getUserId();
        SecurityContextHolder.getContext().
                setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenThrow(UsernameNotFoundException.class);
        //then
        assertThrows(UsernameNotFoundException.class, () -> doctorSecurityService.hasDoctorId(doctorId));
        verify(userSecurityRepository, times(1)).findByLogin(anyString());
    }

    @Test
    @DisplayName("required doctor check")
    void testHasRequiredDoctor() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        Doctor doctor = getFirstDoctor();
        DoctorDto doctorDto = getDoctorDto(getFirstDoctor());
        boolean hasDoctor = false;
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenReturn(Optional.of(doctorSecurity));
        if (doctor.getId() == doctorSecurity.getUserId()) {
            hasDoctor = doctorSecurityService.hasDoctor(doctorDto);
        }
        //then
        assertTrue(hasDoctor);
    }

    @Test
    @DisplayName("required doctor check by invalid userSecurity login")
    void testHasRequiredDoctorWithInvalidLogin() {
        // given
        UserSecurity doctorSecurity = TestDataGenerator.getDoctorSecurity();
        DoctorDto doctorDto = getDoctorDto(getFirstDoctor());
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(doctorSecurity.getLogin(), doctorSecurity.getPassword()));
        // when
        when(userSecurityRepository.findByLogin(anyString())).thenThrow(UsernameNotFoundException.class);
        //then
        assertThrows(UsernameNotFoundException.class, () -> doctorSecurityService.hasDoctor(doctorDto));
        verify(userSecurityRepository, times(1)).findByLogin(anyString());
    }

}
