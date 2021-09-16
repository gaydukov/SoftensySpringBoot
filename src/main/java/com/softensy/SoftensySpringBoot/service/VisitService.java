package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.entity.Visit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    Visit createVisit(long patientId, LocalDateTime dataOfVisit);

    List<PatientDto> getAllVisitsToDoctor(long doctorId);

    List<DoctorDto> getAllPatientVisits(long patientId);

    void deleteVisit(long id);

}
