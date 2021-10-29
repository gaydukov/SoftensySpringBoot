package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;
import com.softensy.softensyspringboot.entity.AbsenceSchedule;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.exception.BadRequestException;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.mapper.AbsenceScheduleMapper;
import com.softensy.softensyspringboot.repository.AbsenceScheduleRepository;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import com.softensy.softensyspringboot.service.AbsenceScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AbsenceScheduleServiceImpl implements AbsenceScheduleService {
    private final AbsenceScheduleRepository absenceScheduleRepository;
    private final AbsenceScheduleMapper absenceScheduleMapper;
    private final DoctorRepository doctorRepository;

    @Override
    public AbsenceScheduleDto createSickLeave(AbsenceScheduleDto absenceScheduleDto) {
        if (absenceScheduleDto.getStartSickLeave() == null) {
            throw new BadRequestException("Field startSickLeave is empty");
        }
        absenceScheduleRepository.save(absenceScheduleMapper.dtoToEntity(absenceScheduleDto));
        return absenceScheduleDto;
    }

    @Override
    public AbsenceScheduleDto closeSickLeave(AbsenceScheduleDto absenceScheduleDto) {
        if ((absenceScheduleDto.getStartSickLeave() == null) || (absenceScheduleDto.getEndSickLeave() == null)) {
            throw new BadRequestException("Sick leave field empty");
        }
        absenceScheduleRepository.save(absenceScheduleMapper.dtoToEntity(absenceScheduleDto));
        return absenceScheduleDto;
    }

    @Override
    public AbsenceScheduleDto createVocation(AbsenceScheduleDto absenceScheduleDto) {
        if ((absenceScheduleDto.getStartVocation() == null) || (absenceScheduleDto.getEndVocation() == null)) {
            throw new BadRequestException("Vocation field empty");
        }
        absenceScheduleRepository.save(absenceScheduleMapper.dtoToEntity(absenceScheduleDto));
        return absenceScheduleDto;
    }

    @Override
    public List<AbsenceScheduleDto> findAllAbsenceScheduleByDoctorId(Long doctorId) {
        List<AbsenceScheduleDto> result = new ArrayList<>();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
        List<AbsenceSchedule> doctorsAbsenceScheduleList = absenceScheduleRepository.findAllByDoctor(doctor);
        for (AbsenceSchedule absenceSchedule : doctorsAbsenceScheduleList) {
            if (!isDoctorAbsent(absenceSchedule, LocalDateTime.now())) {
                result.add(absenceScheduleMapper.entityToDto(absenceSchedule));
            }
        }
        return result;
    }

    public boolean isDoctorAbsent(AbsenceSchedule absenceSchedule, LocalDateTime dateTime) {
        return (((dateTime.isAfter(absenceSchedule.getStartSickLeave())) && (dateTime.isBefore(absenceSchedule.getEndSickLeave())))
                || ((dateTime.isAfter(absenceSchedule.getStartVocation())) && (dateTime.isBefore(absenceSchedule.getEndVocation()))));
    }

}
