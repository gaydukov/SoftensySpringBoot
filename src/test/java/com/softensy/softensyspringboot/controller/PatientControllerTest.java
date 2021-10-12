package com.softensy.softensyspringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.service.PatientService;
import com.softensy.softensyspringboot.service.serviceImpl.PatientSecurityService;
import com.softensy.softensyspringboot.service.serviceImpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstPatient;
import static com.softensy.softensyspringboot.TestDataGenerator.getPatientList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@RunWith(SpringRunner.class)
@ContextConfiguration
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PatientService patientService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean(name = "patientSecurityService")
    private PatientSecurityService patientSecurityService;

    @Test
    @DisplayName("checking get patient by id with authenticated patient with status 200")
    @WithMockUser(authorities = "patient:read")
    void testGetPatientByIdWithAuthenticatedPatientReturnStatus200andPatient() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(patient);
        when(patientSecurityService.hasPatientId(1L)).thenReturn(true);
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
    @DisplayName("checking get patient by id with authenticated doctor with status 200")
    @WithMockUser(authorities = "doctor:read")
    void testGetPatientByIdWithAuthenticatedDoctorReturnStatus200() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking get patient by id with authenticated admin with status 200")
    @WithMockUser(authorities = "admin:read")
    void testGetPatientByIdWithAuthenticatedAdminReturnStatus200() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking forbidden get patient by id with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testGetPatientByIdWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking forbidden get patient by id with another authenticated patient with status 403")
    @WithMockUser(authorities = "patient:read")
    void testGetPatientByIdWithAnotherAuthenticatedPatientReturnStatus403() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(patient);
        when(patientSecurityService.hasPatientId(1L)).thenReturn(false);
        //then
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking save patient with authenticated admin with status 201")
    @WithMockUser(authorities = "admin:write")
    void testAddNewPatientWithAuthenticatedAdminReturnStatus201AndPatient() throws Exception {
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
    @DisplayName("checking forbidden save patient with another authenticated with status 201")
    @WithMockUser(authorities = "patient:write")
    void testAddNewPatientWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.savePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking update patient with authenticated admin with status 200")
    @WithMockUser(authorities = "admin:write")
    void testUpdatePatientWithAuthenticatedAdminReturnStatus200AndPatient() throws Exception {
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
    @DisplayName("checking update patient with authenticated patient with status 200")
    @WithMockUser(authorities = "patient:write")
    void testUpdatePatientWithAuthenticatedPatientReturnStatus200AndPatient() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientSecurityService.hasPatient(patient)).thenReturn(true);
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
    @DisplayName("checking forbidden update patient with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testUpdatePatientWithAnotherAuthenticatedReturnStatus403t() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientSecurityService.hasPatient(patient)).thenReturn(true);
        when(patientService.updatePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(put("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking forbidden update patient with authenticated another patient with status 403")
    @WithMockUser(authorities = "patient:write")
    void testUpdatePatientWithAuthenticatedAnotherPatientReturnStatus403() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientSecurityService.hasPatient(patient)).thenReturn(false);
        when(patientService.updatePatient(patient)).thenReturn(patient);
        //then
        mockMvc.perform(put("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking remove patient with authenticated admin with status 204")
    @WithMockUser(authorities = "admin:write")
    void testRemovePatientFindPatientByIdWithAuthenticatedAdminReturnStatus204() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking remove patient with authenticated patient with status 204")
    @WithMockUser(authorities = "patient:write")
    void testRemovePatientFindPatientByIdWithAuthenticatedPatientReturnStatus204() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientSecurityService.hasPatientId(1L)).thenReturn(true);
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking forbidden remove patient with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testRemovePatientFindPatientByIdWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientSecurityService.hasPatientId(1L)).thenReturn(true);
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking forbidden remove patient with authenticated another patient with status 403")
    @WithMockUser(authorities = "patient:write")
    void testRemovePatientFindPatientByIdWithAuthenticatedAnotherPatientReturnStatus403() throws Exception {
        //given
        Patient patient = getFirstPatient();
        //when
        when(patientSecurityService.hasPatientId(1L)).thenReturn(false);
        when(patientService.getPatientById(1L)).thenReturn(patient);
        //then
        mockMvc.perform(delete("/patient/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking get all patients with authenticated doctor with status 200")
    @WithMockUser(authorities = "doctor:read")
    void testGetAllPatientsWithAuthenticatedDoctorReturnStatus200andListPatients() throws Exception {
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

    @Test
    @DisplayName("checking get all patients with authenticated admin with status 200")
    @WithMockUser(authorities = "admin:read")
    void testGetAllPatientsWithAuthenticatedAdminReturnStatus200() throws Exception {
        //given
        List<Patient> patients = getPatientList();
        //when
        when(patientService.getAllPatients()).thenReturn(patients);
        //then
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking forbidden get all patients with another authenticated with status 403")
    @WithMockUser(authorities = "patient:read")
    void testGetAllPatientsWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        List<Patient> patients = getPatientList();
        //when
        when(patientService.getAllPatients()).thenReturn(patients);
        //then
        mockMvc.perform(get("/patient"))
                .andExpect(status().isForbidden());
    }

}
