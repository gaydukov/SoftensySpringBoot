package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
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
class PatientServiceImplTest {
    @Autowired
    private PatientServiceImpl patientService;
    @MockBean
    private PatientRepository patientRepository;

    @Test
    @DisplayName("get all patients returned list patients")
    void givenPatientsWhenGetAllPatientsThenReturnAllPatients() {
        // before
        List<Patient> expectedListPatients = new ArrayList<>();
        Patient firstPatient = Patient.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        Patient secondPatient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
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
    void givenPatientWhenGetPatientThenReturnPatient() {
        // before
        Patient expectedPatient = Patient.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
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
    void givenPatientWhenAddPatientThenReturnPatientAndVerifyPatientSave() {
        // given
        Patient firstPatient = Patient.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        Patient secondPatient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
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
    void givenPatientWhenUpdatePatientThenReturnPatientAndVerifyPatientUpdate() {
        // given
        Patient firstPatient = Patient.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        Patient secondPatient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
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
    @DisplayName("find patient by id and delete him")
    void givenPatientWhenDeletePatientThenVerifyPatientDelete() {
        Patient firstPatient = Patient.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        // when
        when(patientRepository.findById(1L)).thenReturn(Optional.of(firstPatient));
        patientRepository.delete(firstPatient);
        patientService.deletePatient(firstPatient.getId());
        //then
        Assertions.assertNotNull(firstPatient);
        verify(patientRepository, times(1)).delete(firstPatient);
        verify(patientRepository).delete(firstPatient);
    }
}