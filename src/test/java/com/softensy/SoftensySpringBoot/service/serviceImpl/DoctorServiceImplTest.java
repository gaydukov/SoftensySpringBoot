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
    @DisplayName("Test getAllDoctors")
    void givenDoctors_whenGetAllDoctors_thenReturnAllDoctors() {
        // given
        List<Doctor> expectedDoctors = new ArrayList<>();
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor secondDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
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
    @DisplayName("Test getDoctorById")
    void givenDoctor_whenGetDoctor_thenReturnDoctor() {
        // given
        Doctor expectedDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345);
        expectedDoctor.setId(1L);
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
    @DisplayName("Test saveDoctor")
    void givenDoctor_whenAddDoctor_thenReturnDoctorAndVerifyDoctorSave() {
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor expectedDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
        //when
        when(doctorRepository.save(expectedDoctor)).thenReturn(firstDoctor);
        Doctor actualDoctor = doctorService.saveDoctor(expectedDoctor);
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(expectedDoctor);
        verify(doctorRepository).save(expectedDoctor);
    }

    @Test
    @DisplayName("Test updateDoctor")
    void givenDoctor_whenUpdateDoctor_thenReturnDoctorAndVerifyDoctorUpdate() {
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor expectedDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
        // when
        when(doctorRepository.saveAndFlush(expectedDoctor)).thenReturn(firstDoctor);
        Doctor actualDoctor = doctorService.updateDoctor(expectedDoctor);
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        verify(doctorRepository, times(1)).saveAndFlush(expectedDoctor);
        verify(doctorRepository).saveAndFlush(expectedDoctor);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(expectedDoctor);
    }

    @Test
    @DisplayName("Test deleteDoctor")
    void givenDoctor_whenDeleteDoctor_thenVerifyDoctorDelete() {
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        // when
        doctorRepository.delete(firstDoctor);
        boolean isDoctorDelete = true;
        if (firstDoctor == null) {
            isDoctorDelete = false;
        }
        doctorService.deleteDoctor(firstDoctor.getId());
        //then
        Assertions.assertTrue(isDoctorDelete);
        Assertions.assertNotNull(firstDoctor);
        verify(doctorRepository, times(1)).delete(firstDoctor);
        verify(doctorRepository).delete(firstDoctor);
    }
}