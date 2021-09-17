package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
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
    void whenCreateNewAppointmentThenReturnStatus201AndAppointment() throws Exception {
        //given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment appointment = new Appointment(1, patient, doctor, dateOfAppointment);
        //when
        when(appointmentService.createAppointment(any(Appointment.class))).thenReturn(appointment);
        //then
        mockMvc.perform(post("/appointment")
                        .content(objectMapper.writeValueAsString(appointment))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(appointment)));
    }

    @Test
    @DisplayName("checking get list appointments to doctor by doctor id with status 200")
    void whenGetAppointmentToDoctorByDoctorIdThenReturnStatus200andListPatientsAppointment() throws Exception {
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
                .dateOfAppointment(dateOfFirstAppointment)
                .build();
        PatientDto secondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfAppointment(dateOfSecondAppointment)
                .build();
        PatientDto thirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfAppointment(dateOfThirdAppointment)
                .build();
        List<PatientDto> patientDtoList = new ArrayList<>();
        patientDtoList.add(firstPatient);
        patientDtoList.add(secondPatient);
        patientDtoList.add(thirdPatient);
        //when
        when(appointmentService.getAllAppointmentsToDoctor(anyLong())).thenReturn(patientDtoList);
        //then
        mockMvc.perform(get("/appointment/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(Arrays.asList(firstPatient, secondPatient, thirdPatient))));
    }

    @Test
    @DisplayName("checking get list patient appointments to doctors by patient id with status 200")
    void whenGetPatientAppointmentToDoctorsByPatientIdThenReturnStatus200andListDoctorsAppointment() throws Exception {
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
                .dateOfAppointment(dateOfFirstAppointment)
                .build();
        DoctorDto secondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfAppointment(dateOfSecondAppointment)
                .build();
        DoctorDto thirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfAppointment(dateOfThirdAppointment)
                .build();
        List<DoctorDto> doctorDtoList = new ArrayList<>();
        doctorDtoList.add(firstDoctor);
        doctorDtoList.add(secondDoctor);
        doctorDtoList.add(thirdDoctor);
        //when
        when(appointmentService.getAllPatientAppointments(anyLong())).thenReturn(doctorDtoList);
        //then
        mockMvc.perform(get("/appointment/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(firstDoctor, secondDoctor, thirdDoctor))));
    }

    @Test
    @DisplayName("checking remove appointment with status 204")
    void whenRemoveAppointmentThenReturnStatus204() throws Exception {
        //when
        doNothing().when(appointmentService).deleteAppointment(1L);
        //then
        mockMvc.perform(delete("/appointment/1"))
                .andExpect(status().isNoContent());
    }

}
