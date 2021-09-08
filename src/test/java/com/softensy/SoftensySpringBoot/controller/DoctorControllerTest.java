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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("get doctor by id returned doctor end status")
    void givenIdWhenGetDoctorThenStatus200andDoctorReturned() throws Exception {
        //given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
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
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"));
    }

    @Test
    @DisplayName("save doctor returned doctor end status")
    void givenDoctorWhenAddThenStatus201() throws Exception {
        //given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        //when
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(firstDoctor);
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
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(firstDoctor)));
    }

    @Test
    @DisplayName("update doctor returned doctor end status")
    void givenDoctorWhenUpdateThenStatus200() throws Exception {
        //given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        //when
        when(doctorService.updateDoctor(any(Doctor.class))).thenReturn(firstDoctor);
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
                .andExpect(jsonPath("$.dateOfBirth").value("12-05-1987"))
                .andExpect(jsonPath("$.phoneNumber").value("12345"))
                .andExpect(content().json(objectMapper.writeValueAsString(firstDoctor)));
    }

    @Test
    @DisplayName("delete doctor returned status")
    void givenDoctorWhenDeleteDoctorThenStatus204() throws Exception {
        //given
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        //when
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(firstDoctor));
        //then
        mockMvc.perform(delete("/doctor/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("get all doctors returned doctor end status")
    void givenDoctorsWhenGetDoctorsThenStatus200() throws Exception {
        //given
        List<Doctor> doctors = new ArrayList<>();
        Doctor firstDoctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 05, 12))
                .phoneNumber("12345")
                .build();
        Doctor secondDoctor = Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(LocalDate.of(1988, 07, 19))
                .phoneNumber("54321")
                .build();
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