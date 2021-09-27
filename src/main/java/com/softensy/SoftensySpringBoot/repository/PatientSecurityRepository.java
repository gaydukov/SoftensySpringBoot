package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.PatientSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientSecurityRepository extends JpaRepository<PatientSecurity, Long> {

    Optional<PatientSecurity> findByLogin(String login);

}
