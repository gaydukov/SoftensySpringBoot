package com.softensy.SoftensySpringBoot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;
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

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.softensy.SoftensySpringBoot.TestDataGenerator.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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
        AppointmentDto appointmentDto = getAppointmentDto(getFirstAppointment());
        //when
        when(appointmentService.createAppointment(any(AppointmentDto.class))).thenReturn(appointmentDto);
        //then
        mockMvc.perform(post("/appointment")
                        .content(objectMapper.writeValueAsString(appointmentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.doctorId").value(1))
                .andExpect(jsonPath("$.appointmentDate").value("09:30 01-11-2021"))
                .andExpect(content().json(objectMapper.writeValueAsString(appointmentDto)));
    }

    @Test
    @DisplayName("checking get list appointments to doctor by doctor id with status 200")
    void testGetAppointmentToDoctorByDoctorIdReturnStatus200andListPatientsAppointment() throws Exception {
        // given
        List<DoctorAppointmentDto> doctorAppointmentDtoList = getDoctorAppointmentDtoList();
        //when
        when(appointmentService.getAllDoctorAppointments(anyLong())).thenReturn(doctorAppointmentDtoList);
        //then
        mockMvc.perform(get("/appointment/doctor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.
                        asList(doctorAppointmentDtoList.get(0), doctorAppointmentDtoList.get(1), doctorAppointmentDtoList.get(2)))));
        for (int index = 0; index < doctorAppointmentDtoList.size(); index++) {
            mockMvc.perform(get("/appointment/doctor/1"))
                    .andExpect(jsonPath("$[" + index + "].patientDto.firstName")
                            .value(doctorAppointmentDtoList.get(index).getPatientDto().getFirstName()))
                    .andExpect(jsonPath("$[" + index + "].patientDto.lastName")
                            .value(doctorAppointmentDtoList.get(index).getPatientDto().getLastName()))
                    .andExpect(jsonPath("$[" + index + "].patientDto.middleName")
                            .value(doctorAppointmentDtoList.get(index).getPatientDto().getMiddleName()))
                    .andExpect(jsonPath("$[" + index + "].appointmentDate")
                            .value(doctorAppointmentDtoList.get(index).getAppointmentDate().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))));
        }

    }

    @Test
    @DisplayName("checking get list patient appointments to doctors by patient id with status 200")
    void testGetPatientAppointmentToDoctorsByPatientIdReturnStatus200andListDoctorsAppointment() throws Exception {
        // given
        List<PatientAppointmentDto> patientAppointmentDtoList = getPatientAppointmentDtoList();
        //when
        when(appointmentService.getAllPatientAppointments(anyLong())).thenReturn(patientAppointmentDtoList);
        //then
        mockMvc.perform(get("/appointment/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays
                        .asList(patientAppointmentDtoList.get(0), patientAppointmentDtoList.get(1), patientAppointmentDtoList.get(2)))));
        for (int index = 0; index < patientAppointmentDtoList.size(); index++) {
            mockMvc.perform(get("/appointment/patient/1"))
                    .andExpect(jsonPath("$[" + index + "].doctorDto.firstName")
                            .value(patientAppointmentDtoList.get(index).getDoctorDto().getFirstName()))
                    .andExpect(jsonPath("$[" + index + "].doctorDto.lastName")
                            .value(patientAppointmentDtoList.get(index).getDoctorDto().getLastName()))
                    .andExpect(jsonPath("$[" + index + "].doctorDto.middleName")
                            .value(patientAppointmentDtoList.get(index).getDoctorDto().getMiddleName()))
                    .andExpect(jsonPath("$[" + index + "].appointmentDate")
                            .value(patientAppointmentDtoList.get(index).getAppointmentDate().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))));
        }
    }

    @Test
    @DisplayName("checking remove appointment with status 204")
    void testRemoveAppointmentReturnStatus204() throws Exception {
        //when
        doNothing().when(appointmentService).deleteAppointment(1L);
        //then
        mockMvc.perform(delete("/appointment/1"))
                .andExpect(status().isOk());
        verify(appointmentService, times(1)).deleteAppointment(1L);
    }

}
