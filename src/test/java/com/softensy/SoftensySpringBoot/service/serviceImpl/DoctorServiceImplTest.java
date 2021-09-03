package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepositoty;
import org.junit.jupiter.api.Assertions;
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
    private DoctorRepositoty doctorRepositoty;

    @Test
    void givenDoctors_whenGetAllDoctors_thenReturnAllDoctors() {
        List<Doctor> expectedDoctors = new ArrayList<>();
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor secondDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
        expectedDoctors.add(firstDoctor);
        expectedDoctors.add(secondDoctor);
        // when
        when(doctorRepositoty.findAll()).thenReturn(expectedDoctors);
        List<Doctor> actualDoctors = doctorService.getAllDoctors();
        //then
        Assertions.assertEquals(expectedDoctors, actualDoctors);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(secondDoctor);
        verify(doctorRepositoty).findAll();
        verify(doctorRepositoty, times(1)).findAll();
    }

    @Test
    void givenDoctor_whenGetDoctor_thenReturnDoctor() {
        // given
        Doctor expectedDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345);
        expectedDoctor.setId(1l);
        // when
        when(doctorRepositoty.findById(1l)).thenReturn(Optional.of(expectedDoctor));
        Doctor actualDoctor = doctorService.getDoctorById(1l).get();
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        Assertions.assertNotNull(expectedDoctor);
        verify(doctorRepositoty).findById(1l);
        verify(doctorRepositoty, times(1)).findById(1l);
    }

    @Test
    void givenDoctor_whenAddDoctor_thenReturnDoctorAndVerifyDoctorSave() {
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor expectedDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
        //when
        when(doctorRepositoty.save(expectedDoctor)).thenReturn(firstDoctor);
        Doctor actualDoctor = doctorService.saveDoctor(expectedDoctor);
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(expectedDoctor);
        verify(doctorRepositoty).save(expectedDoctor);
    }

    @Test
    void givenDoctor_whenUpdateDoctor_thenReturnDoctorAndVerifyDoctorUpdate() {
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor expectedDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
        // when
        when(doctorRepositoty.saveAndFlush(expectedDoctor)).thenReturn(firstDoctor);
        Doctor actualDoctor = doctorService.updateDoctor(expectedDoctor);
        //then
        Assertions.assertEquals(expectedDoctor, actualDoctor);
        verify(doctorRepositoty, times(1)).saveAndFlush(expectedDoctor);
        verify(doctorRepositoty).saveAndFlush(expectedDoctor);
        Assertions.assertNotNull(firstDoctor);
        Assertions.assertNotNull(expectedDoctor);
    }

    @Test
    void givenDoctor_whenDeleteDoctor_thenVerifyDoctorDelete() {
        // given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        // when
        doctorRepositoty.delete(firstDoctor);
        boolean isDoctorDelete = true;
        if (firstDoctor == null) {
            isDoctorDelete = false;
        }
        doctorService.deleteDoctor(firstDoctor.getId());
        //then
        Assertions.assertTrue(isDoctorDelete);
        Assertions.assertNotNull(firstDoctor);
        verify(doctorRepositoty, times(1)).delete(firstDoctor);
        verify(doctorRepositoty).delete(firstDoctor);

    }
}