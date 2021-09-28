package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import com.softensy.SoftensySpringBoot.service.serviceImpl.UserDetailsServiceImpl;
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
import java.util.Optional;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.getDoctorList;
import static com.softensy.SoftensySpringBoot.TestDataGenerator.getFirstDoctor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DoctorController.class)
@ContextConfiguration
class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DoctorService doctorService;
    @MockBean(name = "userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("checking get doctor by id with authenticated with status 200")
    @WithMockUser(authorities = ("doctor:read"))
    void testGetDoctorByIdWithAuthenticatedReturnStatus200andDoctor() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        //then
        mockMvc.perform(get("/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.position").value("Hirurg"))
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("45632147"));
    }

    @Test
    @DisplayName("checking forbidden get doctor by id with another authenticated with status 403")
    @WithMockUser(authorities = ("patient:read"))
    void testGetDoctorByIdWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        //then
        mockMvc.perform(get("/doctor/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking save doctor with authenticated status 201")
    @WithMockUser(authorities = ("admin:write"))
    void testAddNewDoctorWithAuthenticatedReturnStatus201AndDoctor() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor);
        //then
        mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.position").value("Hirurg"))
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("45632147"))
                .andExpect(content().json(objectMapper.writeValueAsString(doctor)));
    }

    @Test
    @DisplayName("checking forbidden save doctor with another authenticated status 403")
    @WithMockUser(authorities = ("doctor:write"))
    void testAddNewDoctorWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor);
        //then
        mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking update doctor with authenticated admin with status 200")
    @WithMockUser(authorities = ("admin:write"))
    void testUpdateDoctorWithAuthenticatedAdminReturnStatus200AndDoctor() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(doctorService.updateDoctor(any(Doctor.class))).thenReturn(doctor);
        //then
        mockMvc.perform(put("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.position").value("Hirurg"))
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("45632147"))
                .andExpect(content().json(objectMapper.writeValueAsString(doctor)));
    }

    @Test
    @DisplayName("checking update doctor with authenticated doctor with status 200")
    @WithMockUser(authorities = ("doctor:write"))
    void testUpdateDoctorWithAuthenticatedDoctorReturnStatus200AndDoctor() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(userDetailsService.hasDoctor(doctor)).thenReturn(true);
        when(doctorService.updateDoctor(any(Doctor.class))).thenReturn(doctor);
        //then
        mockMvc.perform(put("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking forbidden update doctor with another authenticated with status 403")
    @WithMockUser(authorities = ("patient:write"))
    void testUpdateDoctorWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(userDetailsService.hasDoctor(doctor)).thenReturn(true);
        when(doctorService.updateDoctor(any(Doctor.class))).thenReturn(doctor);
        //then
        mockMvc.perform(put("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking forbidden update doctor with another authenticated doctor with status 403")
    @WithMockUser(authorities = ("doctor:write"))
    void testUpdateDoctorWithAnotherAuthenticatedDoctorReturnStatus403() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(userDetailsService.hasDoctor(doctor)).thenReturn(false);
        when(doctorService.updateDoctor(any(Doctor.class))).thenReturn(doctor);
        //then
        mockMvc.perform(put("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking remove doctor with authenticated admin with status 200")
    @WithMockUser(authorities = "admin:write")
    void testRemoveDoctorFindDoctorByIdWithAuthenticatedAdminAndReturnStatus204() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        //then
        mockMvc.perform(delete("/doctor/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking remove doctor with authenticated doctor with status 200")
    @WithMockUser(authorities = "doctor:write")
    void testRemoveDoctorFindDoctorByIdWithAuthenticatedDoctorAndReturnStatus204() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(userDetailsService.hasDoctorId(1L)).thenReturn(true);
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        //then
        mockMvc.perform(delete("/doctor/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("checking forbidden remove doctor with another authenticated with status 403")
    @WithMockUser(authorities = "patient:write")
    void testRemoveDoctorFindDoctorByIdWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(userDetailsService.hasDoctorId(1L)).thenReturn(true);
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        //then
        mockMvc.perform(delete("/doctor/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking forbidden remove doctor with authenticated another doctor with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testRemoveDoctorFindDoctorByIdWithAuthenticatedAnotherDoctorReturnStatus403() throws Exception {
        //given
        Doctor doctor = getFirstDoctor();
        //when
        when(userDetailsService.hasDoctorId(1L)).thenReturn(false);
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctor));
        //then
        mockMvc.perform(delete("/doctor/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking get all doctors with authenticated doctor with status 200")
    @WithMockUser(authorities = "doctor:read")
    void testGetAllDoctorsWithAuthenticatedReturnStatus200andListDoctors() throws Exception {
        //given
        List<Doctor> doctors = getDoctorList();
        //when
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        //then
        mockMvc.perform(get("/doctor"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays
                        .asList(doctors.get(0), doctors.get(1), doctors.get(2)))));
    }

    @Test
    @DisplayName("checking forbidden get all doctors with another authenticated with status 403")
    @WithMockUser(authorities = "patient:read")
    void testGetAllDoctorsWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        List<Doctor> doctors = getDoctorList();
        //when
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        //then
        mockMvc.perform(get("/doctor"))
                .andExpect(status().isForbidden());
    }

}
