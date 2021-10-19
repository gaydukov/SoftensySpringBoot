package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;
import com.softensy.softensyspringboot.entity.AbsenceSchedule;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.exception.BadRequestException;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.mapper.AbsenceScheduleMapper;
import com.softensy.softensyspringboot.repository.AbsenceScheduleRepository;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AbsenceScheduleServiceImplTest {
    @Autowired
    private AbsenceScheduleServiceImpl absenceScheduleService;
    @MockBean
    private AbsenceScheduleMapper absenceScheduleMapper;
    @MockBean
    private AbsenceScheduleRepository absenceScheduleRepository;
    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    @DisplayName("checking create new sick leave and saves it")
    void testCreateNewSickLeaveReturnAbsenceScheduleAndVerifyAbsenceScheduleSave() {
        // given
        AbsenceScheduleDto actualAbsenceScheduleDto = getFirstAbsenceScheduleDto();
        AbsenceSchedule absenceSchedule = getFirstAbsenceSchedule();
        // when
        when(absenceScheduleRepository.save(any(AbsenceSchedule.class))).thenReturn(absenceSchedule);
        when(absenceScheduleMapper.dtoToEntity(getFirstAbsenceScheduleDto())).thenReturn(getFirstAbsenceSchedule());
        AbsenceScheduleDto expectedAbsenceScheduleDto = absenceScheduleService.createSickLeave(actualAbsenceScheduleDto);
        //then
        Assertions.assertEquals(expectedAbsenceScheduleDto, actualAbsenceScheduleDto);
        verify(absenceScheduleRepository, times(1)).save(any(AbsenceSchedule.class));
        verify(absenceScheduleMapper, times(1)).dtoToEntity(any(AbsenceScheduleDto.class));
        Assertions.assertNotNull(expectedAbsenceScheduleDto);
    }

    @Test
    @DisplayName("checking create new sick leave with empty absence schedule, expected exception")
    void testCreateNewSickLeaveWithEmptyAbsenceSchedule() {
        assertThrows(BadRequestException.class, () -> {
            final AbsenceScheduleDto invalidAbsenceSchedule = getInvalidAbsenceSchedule();
            absenceScheduleService.createSickLeave(invalidAbsenceSchedule);
        });
    }

    @Test
    @DisplayName("checking update sick leave and saves it")
    void testUpdateSickLeaveReturnAbsenceScheduleAndVerifyAbsenceScheduleSave() {
        // given
        AbsenceScheduleDto actualAbsenceScheduleDto = getFirstAbsenceScheduleDto();
        AbsenceSchedule absenceSchedule = getFirstAbsenceSchedule();
        // when
        when(absenceScheduleRepository.save(any(AbsenceSchedule.class))).thenReturn(absenceSchedule);
        when(absenceScheduleMapper.dtoToEntity(getFirstAbsenceScheduleDto())).thenReturn(getFirstAbsenceSchedule());
        AbsenceScheduleDto expectedAbsenceScheduleDto = absenceScheduleService.closeSickLeave(actualAbsenceScheduleDto);
        //then
        Assertions.assertEquals(expectedAbsenceScheduleDto, actualAbsenceScheduleDto);
        verify(absenceScheduleRepository, times(1)).save(any(AbsenceSchedule.class));
        verify(absenceScheduleMapper, times(1)).dtoToEntity(any(AbsenceScheduleDto.class));
        Assertions.assertNotNull(expectedAbsenceScheduleDto);
    }

    @Test
    @DisplayName("checking update sick leave with empty absence schedule, expected exception")
    void testUpdateSickLeaveWithEmptyAbsenceSchedule() {
        assertThrows(BadRequestException.class, () -> {
            final AbsenceScheduleDto invalidAbsenceSchedule = getInvalidAbsenceSchedule();
            absenceScheduleService.closeSickLeave(invalidAbsenceSchedule);
        });
    }

    @Test
    @DisplayName("checking create new sick leave and saves it")
    void testCreateNewVocationReturnAbsenceScheduleAndVerifyAbsenceScheduleSave() {
        // given
        AbsenceScheduleDto actualAbsenceScheduleDto = getFirstAbsenceScheduleDto();
        AbsenceSchedule absenceSchedule = getFirstAbsenceSchedule();
        // when
        when(absenceScheduleRepository.save(any(AbsenceSchedule.class))).thenReturn(absenceSchedule);
        when(absenceScheduleMapper.dtoToEntity(getFirstAbsenceScheduleDto())).thenReturn(getFirstAbsenceSchedule());
        AbsenceScheduleDto expectedAbsenceScheduleDto = absenceScheduleService.createVocation(actualAbsenceScheduleDto);
        //then
        Assertions.assertEquals(expectedAbsenceScheduleDto, actualAbsenceScheduleDto);
        verify(absenceScheduleRepository, times(1)).save(any(AbsenceSchedule.class));
        verify(absenceScheduleMapper, times(1)).dtoToEntity(any(AbsenceScheduleDto.class));
        Assertions.assertNotNull(expectedAbsenceScheduleDto);
    }

    @Test
    @DisplayName("checking create new vocation with empty absence schedule, expected exception")
    void testCreateNewVocationWithEmptyAbsenceSchedule() {
        assertThrows(BadRequestException.class, () -> {
            final AbsenceScheduleDto invalidAbsenceSchedule = getInvalidAbsenceSchedule();
            absenceScheduleService.createVocation(invalidAbsenceSchedule);
        });
    }

    @Test
    @DisplayName("checking get all absence schedule by doctor id")
    void testFindAllAbsenceScheduleByDoctorIdReturnedAbsenceScheduleDtoList() {
        // given
        Doctor doctor = getFirstDoctor();
        List<AbsenceSchedule> absenceScheduleList = getAbsenceScheduleList();
        List<AbsenceScheduleDto> actualAbsenceScheduleDtoList = getAbsenceScheduleListDto();
        // when
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(doctor));
        when(absenceScheduleRepository.findAllByDoctor(any(Doctor.class))).thenReturn(absenceScheduleList);
        when(absenceScheduleMapper.entityToDto(absenceScheduleList.get(0))).thenReturn(actualAbsenceScheduleDtoList.get(0));
        when(absenceScheduleMapper.entityToDto(absenceScheduleList.get(1))).thenReturn(actualAbsenceScheduleDtoList.get(1));
        when(absenceScheduleMapper.entityToDto(absenceScheduleList.get(2))).thenReturn(actualAbsenceScheduleDtoList.get(2));
        List<AbsenceScheduleDto> expectedAbsenceScheduleDtoList = absenceScheduleService.findAllAbsenceScheduleByDoctorId(2L);
        //then
        Assertions.assertEquals(expectedAbsenceScheduleDtoList, actualAbsenceScheduleDtoList);
        verify(absenceScheduleRepository, times(1)).findAllByDoctor(any(Doctor.class));
        verify(absenceScheduleMapper, times(3)).entityToDto(any(AbsenceSchedule.class));
        verify(doctorRepository, times(1)).findById(anyLong());
        Assertions.assertNotNull(expectedAbsenceScheduleDtoList);
    }

    @Test
    @DisplayName("checking get all absence schedule by doctor id with invalid doctor, expected exception")
    void testFindAllAbsenceScheduleByDoctorIdWithInvalidDoctorReturnedException() {
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> absenceScheduleService.findAllAbsenceScheduleByDoctorId(2L));
    }

    @Test
    @DisplayName("checking is doctor absent")
    void testIsDoctorAbsent() {
        assertTrue(absenceScheduleService.isDoctorAbsent(getFirstAbsenceSchedule(), LocalDateTime.of(2021, 11, 2, 0, 0)));
        assertFalse(absenceScheduleService.isDoctorAbsent(getFirstAbsenceSchedule(), LocalDateTime.of(2021, 11, 16, 0, 0)));
        assertTrue(absenceScheduleService.isDoctorAbsent(getFirstAbsenceSchedule(), LocalDateTime.of(2021, 12, 18, 0, 0)));
        assertFalse(absenceScheduleService.isDoctorAbsent(getFirstAbsenceSchedule(), LocalDateTime.of(2021, 12, 25, 0, 0)));
    }

}
