package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    private PatientController controller;


    @Test
    void givenId_whenGetPatient_thenStatus200andPatientReturned() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //when
        when(patientService.getPatientById(1l)).thenReturn(Optional.of(patient));
        //then
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.doctorId").value("1"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNamber").value("12345"));
    }


    @Test
    void givenPatient_whenAdd_thenStatus201() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //when
        when(patientService.savePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void givenPatient_whenUpdate_thenStatus200() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //when
        when(patientService.updatePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(put("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenPatient_whenDeletePatient_thenStatus204() throws Exception {
        //given
        Patient patient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        patient.setId(1);
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //when
        when(patientService.getPatientById(1l)).thenReturn(Optional.of(patient));
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenPatients_whenGetPatients_thenStatus200andPatientsReturned() throws Exception {
        //given
        List<Patient> patients = new ArrayList<>();
        Patient firstPatient = new Patient("Ivan", "Ivanov", "Ivanovich", 1, Date.valueOf("1987-05-12"), 12345L);
        firstPatient.setId(1);
        Patient secondPatient = new Patient("Petr", "Petrov", "Petrov", 2, Date.valueOf("1988-07-19"), 54321L);
        secondPatient.setId(2);
        patients.add(firstPatient);
        patients.add(secondPatient);
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //when
        when(patientService.getAllPatients()).thenReturn(patients);
        //then
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(firstPatient, secondPatient))));
    }

}