package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
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
    @DisplayName("checking get all doctors")
    void testGetAllDoctorsReturnAllDoctorsAndVerifyFindAll() {
        // given
        List<Doctor> expectedDoctors = new ArrayList<>();
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
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
    @DisplayName("checking get by id doctor")
    void testGetDoctorByIdReturnDoctorAndVerifyFindById() {
        // given
        Doctor expectedDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
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
    @DisplayName("checking save doctor")
    void testAddDoctorReturnDoctorAndVerifyDoctorSave() {
        // given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
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
    @DisplayName("checking update doctor")
    void testUpdateDoctorReturnDoctorAndVerifyDoctorUpdate() {
        // given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
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
    @DisplayName("checking get by id doctor and delete him")
    void testDeleteDoctorByIdVerifyDoctorDelete() {
        // given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        // when
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(firstDoctor));
        when(doctorRepository.existsById(1L)).thenReturn(true);
        doctorRepository.delete(firstDoctor);
        doctorService.deleteDoctor(firstDoctor.getId());
        //then
        Assertions.assertNotNull(firstDoctor);
        verify(doctorRepository, times(1)).delete(firstDoctor);
        verify(doctorRepository).delete(firstDoctor);
    }

}
