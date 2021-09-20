package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .dateOfAppointment(LocalDateTime.of(2021, 9, 19, 00, 33))
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
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfFirstAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondAppointment = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdAppointment = LocalDateTime.of(2021, 11, 4, 11, 30);
        PatientDto firstPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        PatientDto secondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        PatientDto thirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        Map<LocalDateTime, PatientDto> patientDtoList = new HashMap<>();
        patientDtoList.put(dateOfFirstAppointment, firstPatient);
        patientDtoList.put(dateOfSecondAppointment, secondPatient);
        patientDtoList.put(dateOfThirdAppointment, thirdPatient);
        //when
        when(appointmentService.getAllAppointmentsToDoctor(anyLong())).thenReturn(patientDtoList);
        //then
        mockMvc.perform(get("/appointment/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").value(hasKey("2021-11-01T09:30")))
                .andExpect(jsonPath("$").value(hasKey("2021-11-02T10:30")))
                .andExpect(jsonPath("$").value(hasKey("2021-11-04T11:30")));
    }

    @Test
    @DisplayName("checking get list patient appointments to doctors by patient id with status 200")
    void testGetPatientAppointmentToDoctorsByPatientIdReturnStatus200andListDoctorsAppointment() throws Exception {
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
        LocalDateTime dateOfFirstAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondAppointment = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdAppointment = LocalDateTime.of(2021, 11, 4, 11, 30);
        DoctorDto firstDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        DoctorDto secondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        DoctorDto thirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        Map<LocalDateTime, DoctorDto> doctorDtoList = new HashMap<>();
        doctorDtoList.put(dateOfFirstAppointment, firstDoctor);
        doctorDtoList.put(dateOfSecondAppointment, secondDoctor);
        doctorDtoList.put(dateOfThirdAppointment, thirdDoctor);
        //when
        when(appointmentService.getAllPatientAppointments(anyLong())).thenReturn(doctorDtoList);
        //then
        mockMvc.perform(get("/appointment/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").value(hasKey("2021-11-01T09:30")))
                .andExpect(jsonPath("$").value(hasKey("2021-11-02T10:30")))
                .andExpect(jsonPath("$").value(hasKey("2021-11-04T11:30")));
    }

    @Test
    @DisplayName("checking remove appointment with status 204")
    void testRemoveAppointmentReturnStatus204() throws Exception {
        //when
        doNothing().when(appointmentService).deleteAppointment(1L);
        //then
        mockMvc.perform(delete("/appointment/1"))
                .andExpect(status().isNoContent());
    }

}
