package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
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
class PatientServiceImplTest {
    @Autowired
    private PatientServiceImpl patientService;
    @MockBean
    private PatientRepository patientRepository;

    @Test
    @DisplayName("get all patients returned list patients")
    void givenPatients_whenGetAllPatients_thenReturnAllPatients() {
        // before
        List<Patient> expectedListPatients = new ArrayList<>();
        Patient firstPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        firstPatient.setId(1);
        Patient secondPatient = new Patient("Petr", "Petrov", "Petrov", 2, Date.valueOf("1988-07-19"), 54321L);
        expectedListPatients.add(firstPatient);
        expectedListPatients.add(secondPatient);
        // when
        when(patientRepository.findAll()).thenReturn(expectedListPatients);
        List<Patient> actualListPatients = patientService.getAllPatients();
        //then
        Assertions.assertEquals(expectedListPatients, actualListPatients);
        Assertions.assertNotNull(firstPatient);
        Assertions.assertNotNull(secondPatient);
        verify(patientRepository).findAll();
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("get patient by id returned patient")
    void givenPatient_whenGetPatient_thenReturnPatient() {
        // before
        Patient expectedPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        expectedPatient.setId(1);
        // when
        when(patientRepository.findById(1L)).thenReturn(Optional.of(expectedPatient));
        Patient actualPatient = patientService.getPatientById(1L).get();
        //then
        Assertions.assertEquals(expectedPatient, actualPatient);
        Assertions.assertNotNull(expectedPatient);
        verify(patientRepository).findById(1L);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("save patient by id returned patient")
    void givenPatient_whenAddPatient_thenReturnPatientAndVerifyPatientSave() {
        // given
        Patient firstPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        firstPatient.setId(1);
        Patient secondPatient = new Patient("Petr", "Petrov", "Petrov", 1, Date.valueOf("1988-07-19"), 54321L);
        // when
        when(patientRepository.save(secondPatient)).thenReturn(firstPatient);
        Patient actualPatient = patientService.savePatient(secondPatient);
        Patient expectedPatient = firstPatient;
        //then
        Assertions.assertEquals(expectedPatient, actualPatient);
        verify(patientRepository, times(1)).save(secondPatient);
        verify(patientRepository).save(secondPatient);
        Assertions.assertNotNull(firstPatient);
        Assertions.assertNotNull(secondPatient);
    }

    @Test
    @DisplayName("update patient by id returned patient")
    void givenPatient_whenUpdatePatient_thenReturnPatientAndVerifyPatientUpdate() {
        // given
        Patient firstPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        firstPatient.setId(1);
        Patient secondPatient = new Patient("Petr", "Petrov", "Petrov", 1, Date.valueOf("1988-07-19"), 54321L);
        // when
        when(patientRepository.saveAndFlush(secondPatient)).thenReturn(firstPatient);
        Patient actualPatient = patientService.updatePatient(secondPatient);
        Patient expectedPatient = firstPatient;
        //then
        Assertions.assertEquals(expectedPatient, actualPatient);
        verify(patientRepository, times(1)).saveAndFlush(secondPatient);
        verify(patientRepository).saveAndFlush(secondPatient);
        Assertions.assertNotNull(firstPatient);
        Assertions.assertNotNull(secondPatient);
    }

    @Test
    @DisplayName("delete patient")
    void givenPatient_whenDeletePatient_thenVerifyPatientDelete() {
        Patient firstPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        firstPatient.setId(1);
        // when
        patientRepository.delete(firstPatient);
        boolean isPatientDelete = true;
        if (firstPatient == null) {
            isPatientDelete = false;
        }
        patientService.deletePatient(firstPatient.getId());
        //then
        Assertions.assertNotNull(firstPatient);
        Assertions.assertTrue(isPatientDelete);
        verify(patientRepository, times(1)).delete(firstPatient);
        verify(patientRepository).delete(firstPatient);
    }
}