package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.service.DoctorService;
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

@RunWith(SpringRunner.class)
@WebMvcTest(DoctorController.class)
class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DoctorService doctorService;

    @Test
    @DisplayName("Test getDoctorController")
    void givenId_whenGetDoctor_thenStatus200andDoctorReturned() throws Exception {
        //given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        //when
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(firstDoctor));
        //then
        mockMvc.perform(get("/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.position").value("Hirurg"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNamber").value("12345"));
    }

    @Test
    @DisplayName("Test saveDoctorController")
    void givenDoctor_whenAdd_thenStatus201() throws Exception {
        //given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        //when
        when(doctorService.saveDoctor(firstDoctor)).thenReturn(firstDoctor);
        //then
        mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(firstDoctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.position").value("Hirurg"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNamber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(firstDoctor)));
    }

    @Test
    @DisplayName("Test updateDoctorController")
    void givenDoctor_whenUpdate_thenStatus200() throws Exception {
        //given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        //when
        when(doctorService.updateDoctor(firstDoctor)).thenReturn(firstDoctor);
        //then
        mockMvc.perform(put("/doctor")
                        .content(objectMapper.writeValueAsString(firstDoctor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.middleName").value("Ivanovich"))
                .andExpect(jsonPath("$.position").value("Hirurg"))
                .andExpect(jsonPath("$.dateOfBirth").value("11-05-1987"))
                .andExpect(jsonPath("$.phoneNamber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(firstDoctor)));
    }

    @Test
    @DisplayName("Test deleteDoctorController")
    void givenDoctor_whenDeleteDoctor_thenStatus204() throws Exception {
        //given
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        //when
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(firstDoctor));
        //then
        mockMvc.perform(delete("/doctor/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test getAllDoctorsController")
    void givenDoctors_whenGetDoctors_thenStatus200() throws Exception {
        //given
        List<Doctor> doctors = new ArrayList<>();
        Doctor firstDoctor = new Doctor("Ivan", "Ivanov", "Ivanovich", "Hirurg", Date.valueOf("1987-05-12"), 12345L);
        firstDoctor.setId(1);
        Doctor secondDoctor = new Doctor("Petr", "Petrov", "Petrov", "Terapevt", Date.valueOf("1988-07-19"), 54321L);
        secondDoctor.setId(2);
        doctors.add(firstDoctor);
        doctors.add(secondDoctor);
        //when
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        //then
        mockMvc.perform(get("/doctor"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(firstDoctor, secondDoctor))));
    }
}