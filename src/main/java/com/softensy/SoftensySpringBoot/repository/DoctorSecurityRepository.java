package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.DoctorSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorSecurityRepository extends JpaRepository<DoctorSecurity, Long> {

    Optional<DoctorSecurity> findByLogin(String login);

}
