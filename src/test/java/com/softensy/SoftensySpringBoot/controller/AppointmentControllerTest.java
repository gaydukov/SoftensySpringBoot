package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.dto.*;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("checking save appointment with status 201")
    void testCreateNewAppointmentReturnStatus201AndAppointmentDto() throws Exception {
        //given
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .patientId(1L)
                .doctorId(1L)
                .appointmentDate(LocalDateTime.of(2021, 9, 19, 0, 33))
                .build();
        //when
        when(appointmentService.createAppointment(any(AppointmentDto.class))).thenReturn(appointmentDto);
        //then
        mockMvc.perform(post("/appointment")
                        .content(objectMapper.writeValueAsString(appointmentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(appointmentDto)));
    }

    @Test
    @DisplayName("checking get list appointments to doctor by doctor id with status 200")
    void testGetAppointmentToDoctorByDoctorIdReturnStatus200andListPatientsAppointment() throws Exception {
        // given
        LocalDateTime firstAppointmentDate = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime secondAppointmentDate = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime thirdAppointmentDate = LocalDateTime.of(2021, 11, 4, 11, 30);
        PatientDto firstPatient = PatientDto.builder()
                .firstName("Petr")
                .lastName("Pet")
                .middleName("Petrovich")
                .build();
        PatientDto secondPatient = PatientDto.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .build();
        PatientDto thirdPatient = PatientDto.builder()
                .firstName("Semen")
                .lastName("Semenev")
                .middleName("Semenovicyh")
                .build();
        DoctorAppointmentDto firstDoctorAppointmentDto = DoctorAppointmentDto.builder()
                .patientDto(firstPatient)
                .appointmentDate(firstAppointmentDate)
                .build();
        DoctorAppointmentDto secondDoctorAppointmentDto = DoctorAppointmentDto.builder()
                .patientDto(secondPatient)
                .appointmentDate(secondAppointmentDate)
                .build();
        DoctorAppointmentDto thirdDoctorAppointmentDto = DoctorAppointmentDto.builder()
                .patientDto(thirdPatient)
                .appointmentDate(thirdAppointmentDate)
                .build();
        List<DoctorAppointmentDto> doctorAppointmentDtoList = new ArrayList<>();
        doctorAppointmentDtoList.add(firstDoctorAppointmentDto);
        doctorAppointmentDtoList.add(secondDoctorAppointmentDto);
        doctorAppointmentDtoList.add(thirdDoctorAppointmentDto);
        //when
        when(appointmentService.getAllDoctorAppointments(anyLong())).thenReturn(doctorAppointmentDtoList);
        //then
        mockMvc.perform(get("/appointment/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.
                        writeValueAsString(Arrays.asList(firstDoctorAppointmentDto, secondDoctorAppointmentDto, thirdDoctorAppointmentDto))));
    }

    @Test
    @DisplayName("checking get list patient appointments to doctors by patient id with status 200")
    void testGetPatientAppointmentToDoctorsByPatientIdReturnStatus200andListDoctorsAppointment() throws Exception {
        // given
        LocalDateTime firstAppointmentDate = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime secondAppointmentDate = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime thirdAppointmentDate = LocalDateTime.of(2021, 11, 4, 11, 30);
        DoctorDto firstDoctor = DoctorDto.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .build();
        DoctorDto secondDoctor = DoctorDto.builder()
                .firstName("Petr")
                .lastName("Pet")
                .middleName("Petrovich")
                .position("Terape")
                .build();
        DoctorDto thirdDoctor = DoctorDto.builder()
                .firstName("Semen")
                .lastName("Semenev")
                .middleName("Semenovicyh")
                .position("Okulist")
                .build();
        PatientAppointmentDto firstPatientAppointmentDto = PatientAppointmentDto.builder()
                .doctorDto(firstDoctor)
                .appointmentDate(firstAppointmentDate)
                .build();
        PatientAppointmentDto secondPatientAppointmentDto = PatientAppointmentDto.builder()
                .doctorDto(secondDoctor)
                .appointmentDate(secondAppointmentDate)
                .build();
        PatientAppointmentDto thirdPatientAppointmentDto = PatientAppointmentDto.builder()
                .doctorDto(thirdDoctor)
                .appointmentDate(thirdAppointmentDate)
                .build();
        List<PatientAppointmentDto> doctorDtoList = new ArrayList<>();
        doctorDtoList.add(firstPatientAppointmentDto);
        doctorDtoList.add(secondPatientAppointmentDto);
        doctorDtoList.add(thirdPatientAppointmentDto);
        //when
        when(appointmentService.getAllPatientAppointments(anyLong())).thenReturn(doctorDtoList);
        //then
        mockMvc.perform(get("/appointment/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.
                        writeValueAsString(Arrays.asList(firstPatientAppointmentDto, secondPatientAppointmentDto, thirdPatientAppointmentDto))));
    }

    @Test
    @DisplayName("checking remove appointment with status 204")
    void testRemoveAppointmentReturnStatus204() throws Exception {
        //when
        doNothing().when(appointmentService).deleteAppointment(1L);
        //then
        mockMvc.perform(delete("/appointment/1"))
                .andExpect(status().isNoContent());
        verify(appointmentService, times(1)).deleteAppointment(1L);
    }

}
