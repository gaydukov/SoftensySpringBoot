package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceImplTest {
    @Autowired
    private DoctorServiceImpl doctorService;
    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    @DisplayName("get all doctors return list doctors")
    void givenDoctors_whenGetAllDoctors_thenReturnAllDoctors() {
        // given
        List<Doctor> expectedDoctors = new ArrayList<>();
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(Date.valueOf("1987-05-12"))
                .phoneNumber(45632147)
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(Date.valueOf("1988-07-19"))
                .phoneNumber(54321L)
                .build();
        expectedDoctors.add(firstDoctor);
        expectedDoctors.add(secondDoctor);
        // when
        when(doctorRepository.findAll()).thenReturn(expectedDoctors);
        List<Doctor> actualDoctors = doctorService.getAllDoctors();
        //then
        Assertions.assertEquals(expectedDoctors, actualDoctors);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(secondDoctor);
        verify(doctorRepository).findAll();
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("get doctor by id returned doctor")
    void givenDoctor_whenGetDoctor_thenReturnDoctor() {
        // given
        Doctor expectedDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(Date.valueOf("1987-05-12"))
                .phoneNumber(45632147)
                .build();
        // when
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(expectedDoctor));
        Doctor actualDoctor = doctorService.getDoctorById(1L).get();
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        Assertions.assertNotNull(expectedDoctor);
        verify(doctorRepository).findById(1L);
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("save doctor returned doctor")
    void givenDoctor_whenAddDoctor_thenReturnDoctorAndVerifyDoctorSave() {
        // given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(Date.valueOf("1987-05-12"))
                .phoneNumber(45632147)
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(Date.valueOf("1988-07-19"))
                .phoneNumber(54321L)
                .build();
        // when
        when(doctorRepository.save(secondDoctor)).thenReturn(firstDoctor);
        Doctor actualDoctor = doctorService.saveDoctor(secondDoctor);
        Doctor expectedDoctor = firstDoctor;
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        verify(doctorRepository, times(1)).save(secondDoctor);
        verify(doctorRepository).save(secondDoctor);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(secondDoctor);
    }

    @Test
    @DisplayName("update doctor returned doctor")
    void givenDoctor_whenUpdateDoctor_thenReturnDoctorAndVerifyDoctorUpdate() {
        // given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(Date.valueOf("1987-05-12"))
                .phoneNumber(45632147)
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(Date.valueOf("1988-07-19"))
                .phoneNumber(54321L)
                .build();
        // when
        when(doctorRepository.saveAndFlush(secondDoctor)).thenReturn(firstDoctor);
        Doctor actualDoctor = doctorService.updateDoctor(secondDoctor);
        Doctor expectedDoctor = firstDoctor;
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        verify(doctorRepository, times(1)).saveAndFlush(secondDoctor);
        verify(doctorRepository).saveAndFlush(secondDoctor);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(secondDoctor);
    }

    @Test
    @DisplayName("delete doctor")
    void givenDoctor_whenDeleteDoctor_thenVerifyDoctorDelete() {
        // given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(Date.valueOf("1987-05-12"))
                .phoneNumber(45632147)
                .build();
        // when
        doctorRepository.delete(firstDoctor);
        doctorService.deleteDoctor(firstDoctor.getId());
        //then
        Assertions.assertNotNull(firstDoctor);
        verify(doctorRepository, times(1)).delete(firstDoctor);
        verify(doctorRepository).delete(firstDoctor);
    }
}