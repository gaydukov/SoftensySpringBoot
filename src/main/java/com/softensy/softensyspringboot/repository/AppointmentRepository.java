package com.softensy.softensyspringboot.repository;

import com.softensy.softensyspringboot.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByDoctorId(long doctorId);

    List<Appointment> findAllByPatientId(long patientId);

}
