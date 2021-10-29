package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.exception.BadRequestException;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
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
    @DisplayName("checking get all patients with empty list, expected exception")
    void testGetAllPatientsWithEmptyListReturnException() {
        // when
        when(patientRepository.findAll()).thenThrow(NotFoundException.class);
        //then
        Assertions.assertThrows(NotFoundException.class, () -> patientService.getAllPatients());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking get by id patient")
    void testGetPatientByIdReturnPatientAndVerifyFindById() {
        // before
        Patient expectedPatient = getFirstPatient();
        // when
        when(patientRepository.findById(1L)).thenReturn(Optional.of(expectedPatient));
        Patient actualPatient = patientService.getPatientById(1L);
        //then
        Assertions.assertEquals(expectedPatient, actualPatient);
        Assertions.assertNotNull(expectedPatient);
        verify(patientRepository).findById(1L);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("checking get by invalid id patient, expected exception")
    void testGetPatientByInvalidIdReturnException() {
        // when
        when(patientRepository.findById(1L)).thenThrow(NotFoundException.class);
        //then
        Assertions.assertThrows(NotFoundException.class, () -> patientService.getPatientById(1L));
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
    @DisplayName("checking save invalid patient, expected exception")
    void testAddInvalidPatientReturnException() {
        Assertions.assertThrows(BadRequestException.class, () -> patientService.savePatient(null));
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
    @DisplayName("checking update invalid patient, expected exception")
    void testUpdateInvalidPatientReturnException() {
        Assertions.assertThrows(BadRequestException.class, () -> patientService.updatePatient(null));
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

    @Test
    @DisplayName("checking get by invalid id patient, expected exception")
    void testDeletePatientByInvalidIdReturnerException() {
        when(patientRepository.existsById(1L)).thenReturn(false);
        //then
        Assertions.assertThrows(BadRequestException.class, () -> patientService.deletePatient(1L));
        verify(patientRepository, times(1)).existsById(1L);
    }

}
