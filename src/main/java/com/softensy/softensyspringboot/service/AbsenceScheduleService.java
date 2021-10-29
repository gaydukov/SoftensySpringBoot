package com.softensy.softensyspringboot.service;

import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;

import java.util.List;

public interface AbsenceScheduleService {

    AbsenceScheduleDto createSickLeave(AbsenceScheduleDto absenceScheduleDto);

    AbsenceScheduleDto closeSickLeave(AbsenceScheduleDto absenceScheduleDto);

    AbsenceScheduleDto createVocation(AbsenceScheduleDto absenceScheduleDto);

    List<AbsenceScheduleDto> findAllAbsenceScheduleByDoctorId(Long doctorId);

}
