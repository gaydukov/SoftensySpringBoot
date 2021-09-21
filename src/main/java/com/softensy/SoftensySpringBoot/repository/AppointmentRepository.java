package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByDoctorId(long doctorId);

    List<Appointment> findAllByPatientId(long patientId);

}
