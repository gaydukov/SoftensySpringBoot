package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
