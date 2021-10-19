package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;
import com.softensy.softensyspringboot.entity.AbsenceSchedule;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class AbsenceScheduleMapperTest {
    @Autowired
    private AbsenceScheduleMapper absenceScheduleMapper;
    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    @DisplayName("check mapping absence schedule entity to dto")
    void testMappingAbsenceScheduleEntityToDto() {
        // given
        AbsenceSchedule absenceSchedule = getFirstAbsenceSchedule();
        AbsenceScheduleDto expectedAbsenceScheduleDto = getFirstAbsenceScheduleDto();
        // when
        AbsenceScheduleDto actualAbsenceScheduleDto = absenceScheduleMapper.entityToDto(absenceSchedule);
        //then
        assertEquals(expectedAbsenceScheduleDto.getId(), actualAbsenceScheduleDto.getId());
        assertEquals(expectedAbsenceScheduleDto.getStartSickLeave(), actualAbsenceScheduleDto.getStartSickLeave());
        assertEquals(expectedAbsenceScheduleDto.getEndSickLeave(), actualAbsenceScheduleDto.getEndSickLeave());
        assertEquals(expectedAbsenceScheduleDto.getStartVocation(), actualAbsenceScheduleDto.getStartVocation());
        assertEquals(expectedAbsenceScheduleDto.getEndVocation(), actualAbsenceScheduleDto.getEndVocation());
        assertEquals(expectedAbsenceScheduleDto.getDoctorId(), actualAbsenceScheduleDto.getDoctorId());
        assertEquals(expectedAbsenceScheduleDto, actualAbsenceScheduleDto);
        assertNotNull(expectedAbsenceScheduleDto);
        assertNotNull(actualAbsenceScheduleDto);
    }

    @Test
    @DisplayName("check mapping absence schedule dto to entity")
    void testMappingAbsenceScheduleDtoToEntity() {
        // given
        AbsenceScheduleDto absenceSchedule = getFirstAbsenceScheduleDto();
        AbsenceSchedule expectedAbsenceSchedule = getFirstAbsenceSchedule();
        Doctor doctor = getFirstDoctor();
        // when
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(doctor));
        AbsenceSchedule actualAbsenceSchedule = absenceScheduleMapper.dtoToEntity(absenceSchedule);
        //then
        assertEquals(expectedAbsenceSchedule.getId(), actualAbsenceSchedule.getId());
        assertEquals(expectedAbsenceSchedule.getStartSickLeave(), actualAbsenceSchedule.getStartSickLeave());
        assertEquals(expectedAbsenceSchedule.getEndSickLeave(), actualAbsenceSchedule.getEndSickLeave());
        assertEquals(expectedAbsenceSchedule.getStartVocation(), actualAbsenceSchedule.getStartVocation());
        assertEquals(expectedAbsenceSchedule.getEndVocation(), actualAbsenceSchedule.getEndVocation());
        assertEquals(expectedAbsenceSchedule.getDoctor(), actualAbsenceSchedule.getDoctor());
        assertEquals(expectedAbsenceSchedule, actualAbsenceSchedule);
        assertNotNull(expectedAbsenceSchedule);
        assertNotNull(actualAbsenceSchedule);
    }

    @Test
    @DisplayName("check mapping absence schedule dto to entity with invalid doctor, expected exception")
    void testMappingAbsenceScheduleDtoToEntityWithInvalidDoctor() {
        // given
        AbsenceScheduleDto absenceSchedule = getFirstAbsenceScheduleDto();
        // when
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> absenceScheduleMapper.dtoToEntity(absenceSchedule));
    }

}
