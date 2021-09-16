package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.entity.Visit;
import com.softensy.SoftensySpringBoot.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class VisitServiceImplTest {
    @Autowired
    private VisitServiceImpl visitService;
    @MockBean
    private VisitRepository visitRepository;

    @Test
    @DisplayName("checking create new visit and saves it")
    void whenCreateNewVisitThenReturnVisitAndVerifyVisitSave() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfVisit = LocalDateTime.of(2021, 11, 1, 9, 30);
        Visit actualVisit = new Visit(1, patient, doctor, dateOfVisit);
        // when
        when(visitRepository.save(any(Visit.class))).thenReturn(actualVisit);
        Visit expectedVisit = visitService.createVisit(actualVisit);
        //then
        Assertions.assertEquals(expectedVisit, actualVisit);
        verify(visitRepository, times(1)).save(any(Visit.class));
        verify(visitRepository).save(any(Visit.class));
        Assertions.assertNotNull(doctor);
        Assertions.assertNotNull(patient);
        Assertions.assertNotNull(actualVisit);
    }

    @Test
    @DisplayName("checking all visits to doctor by doctor Id")
    void whenGetAllVisitsToDoctorByDoctorIdReturnListPatientDtoWhoVisitThisDoctor() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfFirstVisit = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondVisit = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdVisit = LocalDateTime.of(2021, 11, 4, 11, 30);
        Visit firstVisit = new Visit(1, patient, doctor, dateOfFirstVisit);
        Visit secondVisit = new Visit(2, patient, doctor, dateOfSecondVisit);
        Visit thirdVisit = new Visit(3, patient, doctor, dateOfThirdVisit);
        List<Visit> visitList = new ArrayList<>();
        visitList.add(firstVisit);
        visitList.add(secondVisit);
        visitList.add(thirdVisit);
        PatientDto actualFirstPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfVisit(dateOfFirstVisit)
                .build();
        PatientDto actualSecondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfVisit(dateOfSecondVisit)
                .build();
        PatientDto actualThirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfVisit(dateOfThirdVisit)
                .build();
        List<PatientDto> actualPatientList = new ArrayList<>();
        actualPatientList.add(actualFirstPatient);
        actualPatientList.add(actualSecondPatient);
        actualPatientList.add(actualThirdPatient);
        // when
        when(visitRepository.findAll()).thenReturn(visitList);
        List<PatientDto> expectedPatientList = visitService.getAllVisitsToDoctor(1L);
        //then
        Assertions.assertEquals(expectedPatientList, actualPatientList);
        Assertions.assertNotNull(expectedPatientList);
        Assertions.assertNotNull(actualPatientList);
        verify(visitRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking all patient's visits to doctor by patient Id")
    void whenGetAllPatientVisitsByPatientIdReturnListDoctorDtoWhichThisPatientIsAssigned() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfFirstVisit = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondVisit = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdVisit = LocalDateTime.of(2021, 11, 4, 11, 30);
        Visit firstVisit = new Visit(1, patient, doctor, dateOfFirstVisit);
        Visit secondVisit = new Visit(2, patient, doctor, dateOfSecondVisit);
        Visit thirdVisit = new Visit(3, patient, doctor, dateOfThirdVisit);
        List<Visit> visitList = new ArrayList<>();
        visitList.add(firstVisit);
        visitList.add(secondVisit);
        visitList.add(thirdVisit);
        DoctorDto actualFirstDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfVisit(dateOfFirstVisit)
                .build();
        DoctorDto actualSecondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfVisit(dateOfSecondVisit)
                .build();
        DoctorDto actualThirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfVisit(dateOfThirdVisit)
                .build();
        List<DoctorDto> actualDoctorList = new ArrayList<>();
        actualDoctorList.add(actualFirstDoctor);
        actualDoctorList.add(actualSecondDoctor);
        actualDoctorList.add(actualThirdDoctor);
        // when
        when(visitRepository.findAll()).thenReturn(visitList);
        List<DoctorDto> expectedDoctorList = visitService.getAllPatientVisits(2L);
        //then
        Assertions.assertEquals(expectedDoctorList, actualDoctorList);
        Assertions.assertNotNull(expectedDoctorList);
        Assertions.assertNotNull(actualDoctorList);
        verify(visitRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking get by id visit and delete him")
    void whenDeleteVisitByIdThenVerifyVisitDelete() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfVisit = LocalDateTime.of(2021, 11, 1, 9, 30);
        Visit visit = new Visit(1, patient, doctor, dateOfVisit);
        // when
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        when(visitRepository.existsById(1L)).thenReturn(true);
        visitRepository.deleteById(1L);
        visitService.deleteVisit(1L);
        //then
        Assertions.assertNotNull(visit);
        verify(visitRepository, times(2)).deleteById(visit.getId());
    }

}
