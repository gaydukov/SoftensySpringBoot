package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
