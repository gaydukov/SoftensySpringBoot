package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
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
class PatientServiceImplTest {
    @Autowired
    private PatientServiceImpl patientService;
    @MockBean
    private PatientRepository patientRepository;

    @Test
    @DisplayName("checking get all patients")
    void testGetAllPatientsReturnAllPatientsAndVerifyFindAll() {
        // before
        List<Patient> expectedListPatients = getPatientList();
        // when
        when(patientRepository.findAll()).thenReturn(expectedListPatients);
        List<Patient> actualListPatients = patientService.getAllPatients();
        //then
        Assertions.assertEquals(expectedListPatients, actualListPatients);
        Assertions.assertNotNull(expectedListPatients.get(0));
        Assertions.assertNotNull(expectedListPatients.get(1));
        Assertions.assertNotNull(expectedListPatients.get(2));
        verify(patientRepository).findAll();
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking get by id patient")
    void testGetPatientByIdReturnPatientAndVerifyFindById() {
        // before
        Patient expectedPatient = getFirstPatient();
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
    @DisplayName("checking save patient")
    void testAddPatientReturnPatientAndVerifyPatientSave() {
        // given
        Patient firstPatient = getFirstPatient();
        Patient secondPatient = getSecondPatient();
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
    @DisplayName("checking update patient")
    void testUpdatePatientReturnPatientAndVerifyPatientUpdate() {
        // given
        Patient firstPatient = getFirstPatient();
        Patient secondPatient = getSecondPatient();
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
    @DisplayName("checking get by id patient and delete him")
    void testDeletePatientByIdFindPatientByIdAndVerifyPatientDelete() {
        Patient patient = getFirstPatient();
        // when
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.existsById(1L)).thenReturn(true);
        patientRepository.delete(patient);
        patientService.deletePatient(patient.getId());
        //then
        Assertions.assertNotNull(patient);
        verify(patientRepository, times(1)).delete(patient);
        verify(patientRepository).delete(patient);
    }

}
