package com.softensy.softensyspringboot.repository;

import com.softensy.softensyspringboot.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
