package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entety.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepositoty extends JpaRepository<Doctor,Long> {
}
