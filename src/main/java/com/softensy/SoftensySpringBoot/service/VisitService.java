package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Visit;

import java.util.List;

public interface VisitService {

    Visit createVisit(Visit visit);

    List<PatientDto> getAllVisitsToDoctor(long doctorId);

    List<DoctorDto> getAllPatientVisits(long patientId);

    void deleteVisit(long id);

}
