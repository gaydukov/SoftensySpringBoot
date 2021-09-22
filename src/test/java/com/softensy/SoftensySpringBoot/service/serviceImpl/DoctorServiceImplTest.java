package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.*;
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
        List<Doctor> expectedDoctors = getDoctorList();
        // when
        when(doctorRepository.findAll()).thenReturn(expectedDoctors);
        List<Doctor> actualDoctors = doctorService.getAllDoctors();
        //then
        Assertions.assertEquals(expectedDoctors, actualDoctors);
        Assertions.assertNotNull(expectedDoctors.get(0));
        Assertions.assertNotNull(expectedDoctors.get(1));
        Assertions.assertNotNull(expectedDoctors.get(2));
        verify(doctorRepository).findAll();
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking get by id doctor")
    void testGetDoctorByIdReturnDoctorAndVerifyFindById() {
        // given
        Doctor expectedDoctor = getFirstDoctor();
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
        Doctor firstDoctor = getFirstDoctor();
        Doctor secondDoctor = getSecondDoctor();
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
        Doctor firstDoctor = getFirstDoctor();
        Doctor secondDoctor = getSecondDoctor();
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
        Doctor doctor = getFirstDoctor();
        // when
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.existsById(1L)).thenReturn(true);
        doctorRepository.delete(doctor);
        doctorService.deleteDoctor(doctor.getId());
        //then
        Assertions.assertNotNull(doctor);
        verify(doctorRepository, times(1)).delete(doctor);
        verify(doctorRepository).delete(doctor);
    }

}
