package com.softensy.softensyspringboot.repository;

import com.softensy.softensyspringboot.entity.AbsenceSchedule;
import com.softensy.softensyspringboot.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceScheduleRepository extends JpaRepository<AbsenceSchedule, Long> {

    List<AbsenceSchedule> findAllByDoctor(Doctor doctor);

}
