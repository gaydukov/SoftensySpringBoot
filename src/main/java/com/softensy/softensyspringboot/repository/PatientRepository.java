package com.softensy.softensyspringboot.repository;

import com.softensy.softensyspringboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
