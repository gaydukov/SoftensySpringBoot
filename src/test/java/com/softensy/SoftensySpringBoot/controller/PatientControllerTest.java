package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@RunWith(SpringRunner.class)
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PatientService patientService;

    @Test
    @DisplayName("get patient by id returned doctor end status")
    void givenId_whenGetPatient_thenStatus200andPatientReturned() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        //when
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
        //then
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.doctorId").value("1"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"));
    }

    @Test
    @DisplayName("save patient returned doctor end status")
    void givenPatient_whenAdd_thenStatus201() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        //when
        when(patientService.savePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.doctorId").value("1"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    @DisplayName("update patient returned doctor end status")
    void givenPatient_whenUpdate_thenStatus200() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        //when
        when(patientService.updatePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(put("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.doctorId").value("1"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    @DisplayName("delete patient returned status")
    void givenPatient_whenDeletePatient_thenStatus204() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        //when
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("get all patients returned doctor end status")
    void givenPatients_whenGetPatients_thenStatus200andPatientsReturned() throws Exception {
        //given
        List<Patient> patients = new ArrayList<>();
        Patient firstPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        firstPatient.setId(1);
        Patient secondPatient = new Patient("Petr", "Petrov", "Petrov", 2, Date.valueOf("1988-07-19"), 54321L);
        secondPatient.setId(2);
        patients.add(firstPatient);
        patients.add(secondPatient);
        //when
        when(patientService.getAllPatients()).thenReturn(patients);
        //then
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(firstPatient, secondPatient))));
    }
}