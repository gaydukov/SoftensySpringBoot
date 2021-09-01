package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepositoty extends JpaRepository<Doctor,Long> {
}
