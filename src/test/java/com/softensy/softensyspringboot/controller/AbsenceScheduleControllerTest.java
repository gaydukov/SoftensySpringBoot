package com.softensy.softensyspringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.softensyspringboot.TestDataGenerator;
import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;
import com.softensy.softensyspringboot.service.serviceimpl.AbsenceScheduleServiceImpl;
import com.softensy.softensyspringboot.service.serviceimpl.UserDetailsServiceImpl;
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

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstAbsenceScheduleDto;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AbsenceScheduleController.class)
@ContextConfiguration()
class AbsenceScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AbsenceScheduleServiceImpl absenceScheduleService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("checking save sick leave with authenticated with status 201")
    @WithMockUser(authorities = "admin:write")
    void testCreateSickLeaveWithAuthenticatedReturnStatus201AndAbsenceSchedule() throws Exception {
        //given
        AbsenceScheduleDto absenceScheduleDto = getFirstAbsenceScheduleDto();
        //when
        when(absenceScheduleService.createSickLeave(any(AbsenceScheduleDto.class))).thenReturn(absenceScheduleDto);
        //then
        mockMvc.perform(post("/schedule/sickleave")
                        .content(objectMapper.writeValueAsString(absenceScheduleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.startSickLeave").value("00:00 01-11-2021"))
                .andExpect(jsonPath("$.endSickLeave").value("00:00 14-11-2021"))
                .andExpect(jsonPath("$.startVocation").value("00:00 01-12-2021"))
                .andExpect(jsonPath("$.endVocation").value("00:00 21-12-2021"))
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(content().json(objectMapper.writeValueAsString(absenceScheduleDto)));
    }

    @Test
    @DisplayName("checking save sick leave with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testCreateSickLeaveWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        AbsenceScheduleDto absenceScheduleDto = getFirstAbsenceScheduleDto();
        //when
        when(absenceScheduleService.createSickLeave(any(AbsenceScheduleDto.class))).thenReturn(absenceScheduleDto);
        //then
        mockMvc.perform(post("/schedule/sickleave")
                        .content(objectMapper.writeValueAsString(absenceScheduleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking save vocation with authenticated with status 201")
    @WithMockUser(authorities = "admin:write")
    void testCreateVocationWithAuthenticatedReturnStatus201AndAbsenceSchedule() throws Exception {
        //given
        AbsenceScheduleDto absenceScheduleDto = getFirstAbsenceScheduleDto();
        //when
        when(absenceScheduleService.createVocation(any(AbsenceScheduleDto.class))).thenReturn(absenceScheduleDto);
        //then
        mockMvc.perform(post("/schedule/vocation")
                        .content(objectMapper.writeValueAsString(absenceScheduleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.startSickLeave").value("00:00 01-11-2021"))
                .andExpect(jsonPath("$.endSickLeave").value("00:00 14-11-2021"))
                .andExpect(jsonPath("$.startVocation").value("00:00 01-12-2021"))
                .andExpect(jsonPath("$.endVocation").value("00:00 21-12-2021"))
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(content().json(objectMapper.writeValueAsString(absenceScheduleDto)));
    }

    @Test
    @DisplayName("checking save vocation with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testCreateVocationWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        AbsenceScheduleDto absenceScheduleDto = getFirstAbsenceScheduleDto();
        //when
        when(absenceScheduleService.createVocation(any(AbsenceScheduleDto.class))).thenReturn(absenceScheduleDto);
        //then
        mockMvc.perform(post("/schedule/sickleave")
                        .content(objectMapper.writeValueAsString(absenceScheduleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking close sick leave with authenticated with status 201")
    @WithMockUser(authorities = "admin:write")
    void testUpdateSickLeaveWithAuthenticatedReturnStatus200AndAbsenceSchedule() throws Exception {
        //given
        AbsenceScheduleDto absenceScheduleDto = getFirstAbsenceScheduleDto();
        //when
        when(absenceScheduleService.closeSickLeave(any(AbsenceScheduleDto.class))).thenReturn(absenceScheduleDto);
        //then
        mockMvc.perform(put("/schedule/sickleave")
                        .content(objectMapper.writeValueAsString(absenceScheduleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.startSickLeave").value("00:00 01-11-2021"))
                .andExpect(jsonPath("$.endSickLeave").value("00:00 14-11-2021"))
                .andExpect(jsonPath("$.startVocation").value("00:00 01-12-2021"))
                .andExpect(jsonPath("$.endVocation").value("00:00 21-12-2021"))
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(content().json(objectMapper.writeValueAsString(absenceScheduleDto)));
    }

    @Test
    @DisplayName("checking close sick leave with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testUpdateSickLeaveWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        AbsenceScheduleDto absenceScheduleDto = getFirstAbsenceScheduleDto();
        //when
        when(absenceScheduleService.closeSickLeave(any(AbsenceScheduleDto.class))).thenReturn(absenceScheduleDto);
        //then
        mockMvc.perform(put("/schedule/sickleave")
                        .content(objectMapper.writeValueAsString(absenceScheduleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking get all absence schedule by doctor id with authenticated with status 201")
    @WithMockUser(authorities = "admin:read")
    void testGetAbsenceScheduleListWithAuthenticatedReturnStatus200AndAbsenceScheduleList() throws Exception {
        // given
        List<AbsenceScheduleDto> absenceScheduleList = TestDataGenerator.getAbsenceScheduleListDto();
        //when
        when(absenceScheduleService.findAllAbsenceScheduleByDoctorId(anyLong())).thenReturn(absenceScheduleList);
        //then
        mockMvc.perform(get("/schedule/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.
                        asList(absenceScheduleList.get(0), absenceScheduleList.get(1), absenceScheduleList.get(2)))));
        for (int index = 0; index < absenceScheduleList.size(); index++) {
            mockMvc.perform(get("/schedule/1"))
                    .andExpect(jsonPath("$[" + index + "].id")
                            .value(absenceScheduleList.get(index).getId()))
                    .andExpect(jsonPath("$[" + index + "].doctorId")
                            .value(absenceScheduleList.get(index).getDoctorId()))
                    .andExpect(jsonPath("$[" + index + "].startSickLeave")
                            .value(absenceScheduleList.get(index).getStartSickLeave().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))))
                    .andExpect(jsonPath("$[" + index + "].endSickLeave")
                            .value(absenceScheduleList.get(index).getEndSickLeave().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))))
                    .andExpect(jsonPath("$[" + index + "].startVocation")
                            .value(absenceScheduleList.get(index).getStartVocation().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))))
                    .andExpect(jsonPath("$[" + index + "].endVocation")
                            .value(absenceScheduleList.get(index).getEndVocation().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))));
        }
    }

    @Test
    @DisplayName("checking get all absence schedule by doctor id with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testGetAbsenceScheduleListWithAnotherAuthenticatedReturnStatus403() throws Exception {
        // given
        List<AbsenceScheduleDto> absenceScheduleList = TestDataGenerator.getAbsenceScheduleListDto();
        //when
        when(absenceScheduleService.findAllAbsenceScheduleByDoctorId(anyLong())).thenReturn(absenceScheduleList);
        //then
        mockMvc.perform(get("/schedule/1"))
                .andExpect(status().isForbidden());
    }

}
