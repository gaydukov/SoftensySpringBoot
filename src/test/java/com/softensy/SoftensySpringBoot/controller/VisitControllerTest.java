package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.entity.Visit;
import com.softensy.SoftensySpringBoot.service.VisitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VisitController.class)
class VisitControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VisitService visitService;

    @Test
    @DisplayName("checking save visit with status 201")
    void whenCreateNewVisitThenReturnStatus201AndVisit() throws Exception {
        //given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfVisit = LocalDateTime.of(2021, 11, 01, 9, 30);
        Visit visit = new Visit(1, patient, doctor, dateOfVisit);
        //when
        when(visitService.createVisit(any(Visit.class))).thenReturn(visit);
        //then
        mockMvc.perform(post("/visit")
                        .content(objectMapper.writeValueAsString(visit))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(visit)));
    }

    @Test
    @DisplayName("checking get list visits to doctor by doctor id with status 200")
    void whenGetVisitToDoctorByDoctorIdThenReturnStatus200andListPatientsVisit() throws Exception {
        // given
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfFirstVisit = LocalDateTime.of(2021, 11, 01, 9, 30);
        LocalDateTime dateOfSecondVisit = LocalDateTime.of(2021, 11, 02, 10, 30);
        LocalDateTime dateOfThirdVisit = LocalDateTime.of(2021, 11, 04, 11, 30);
        PatientDto firstPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfVisit(dateOfFirstVisit)
                .build();
        PatientDto secondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfVisit(dateOfSecondVisit)
                .build();
        PatientDto thirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfVisit(dateOfThirdVisit)
                .build();
        List<PatientDto> patientDtoList = new ArrayList<>();
        patientDtoList.add(firstPatient);
        patientDtoList.add(secondPatient);
        patientDtoList.add(thirdPatient);
        //when
        when(visitService.getAllVisitsToDoctor(anyLong())).thenReturn(patientDtoList);
        //then
        mockMvc.perform(get("/visit/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(Arrays.asList(firstPatient, secondPatient, thirdPatient))));
    }

    @Test
    @DisplayName("checking get list patient visits to doctors by patient id with status 200")
    void whenGetPatientVisitToDoctorsByPatientIdThenReturnStatus200andListDoctorsVisit() throws Exception {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("45632147")
                .build();
        LocalDateTime dateOfFirstVisit = LocalDateTime.of(2021, 11, 01, 9, 30);
        LocalDateTime dateOfSecondVisit = LocalDateTime.of(2021, 11, 02, 10, 30);
        LocalDateTime dateOfThirdVisit = LocalDateTime.of(2021, 11, 04, 11, 30);
        DoctorDto firstDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfVisit(dateOfFirstVisit)
                .build();
        DoctorDto secondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfVisit(dateOfSecondVisit)
                .build();
        DoctorDto thirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfVisit(dateOfThirdVisit)
                .build();
        List<DoctorDto> doctorDtoList = new ArrayList<>();
        doctorDtoList.add(firstDoctor);
        doctorDtoList.add(secondDoctor);
        doctorDtoList.add(thirdDoctor);
        //when
        when(visitService.getAllPatientVisits(anyLong())).thenReturn(doctorDtoList);
        //then
        mockMvc.perform(get("/visit/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(firstDoctor, secondDoctor, thirdDoctor))));
    }

    @Test
    @DisplayName("checking remove visit with status 204")
    void whenRemoveVisitThenFindVisitByIdAndReturnStatus204() throws Exception {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfVisit = LocalDateTime.of(2021, 11, 01, 9, 30);
        Visit visit = new Visit(1, patient, doctor, dateOfVisit);
        //when
        // when(visitService.deleteVisit(1L)).;
        doNothing().when(visitService).deleteVisit(1L);
        //then
        mockMvc.perform(delete("/visit/1"))
                .andExpect(status().isNoContent());
    }

}
