package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;
import com.softensy.softensyspringboot.entity.AbsenceSchedule;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AbsenceScheduleMapper {
    private final DoctorRepository doctorRepository;

    public AbsenceSchedule dtoToEntity(AbsenceScheduleDto absenceScheduleDto) {
        Doctor doctor = doctorRepository.findById(absenceScheduleDto.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
        return AbsenceSchedule.builder()
                .id(absenceScheduleDto.getId())
                .doctor(doctor)
                .startSickLeave(absenceScheduleDto.getStartSickLeave())
                .endSickLeave(absenceScheduleDto.getEndSickLeave())
                .startVocation(absenceScheduleDto.getStartVocation())
                .endVocation(absenceScheduleDto.getEndVocation())
                .build();
    }

    public AbsenceScheduleDto entityToDto(AbsenceSchedule absenceSchedule) {
        return AbsenceScheduleDto.builder()
                .id(absenceSchedule.getId())
                .doctorId(absenceSchedule.getDoctor().getId())
                .startSickLeave(absenceSchedule.getStartSickLeave())
                .endSickLeave(absenceSchedule.getEndSickLeave())
                .startVocation(absenceSchedule.getStartVocation())
                .endVocation(absenceSchedule.getEndVocation())
                .build();
    }

}
