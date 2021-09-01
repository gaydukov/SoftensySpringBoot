package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
