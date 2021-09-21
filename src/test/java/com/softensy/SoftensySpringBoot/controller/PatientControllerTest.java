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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.softensy.SoftensySpringBoot.TestDataGeneration.getFirstPatient;
import static com.softensy.SoftensySpringBoot.TestDataGeneration.getPatientList;
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
    @DisplayName("checking get patient by id with status 200")
    void testGetPatientByIdReturnStatus200andPatient() throws Exception {
        //given
        Patient patient = getFirstPatient();
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
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"));
    }

    @Test
    @DisplayName("checking save patient with status 201")
    void testAddNewPatientReturnStatus201AndPatient() throws Exception {
        //given
        Patient patient = getFirstPatient();
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
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    @DisplayName("checking update patient with status 200")
    void testUpdatePatientReturnStatus200AndPatient() throws Exception {
        //given
        Patient patient = getFirstPatient();
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
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    @DisplayName("checking remove patient with status 204")
    void testRemovePatientFindPatientByIdAndReturnStatus204() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("checking get all patients with status 200")
    void testGetAllPatientsReturnStatus200andListPatients() throws Exception {
        //given
        List<Patient> patients = getPatientList();
        //when
        when(patientService.getAllPatients()).thenReturn(patients);
        //then
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays
                        .asList(patients.get(0), patients.get(1), patients.get(2)))));
    }

}
