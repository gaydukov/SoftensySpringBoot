package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Visit;
import com.softensy.SoftensySpringBoot.repository.VisitRepository;
import com.softensy.SoftensySpringBoot.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit createVisit(Visit visit) {
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Visit not found");
        }
        return visitRepository.save(visit);
    }

    @Override
    public List<PatientDto> getAllVisitsToDoctor(long doctorId) {
        List<Visit> visits = visitRepository.findAll();
        List<PatientDto> result = new ArrayList<>();
        Visit visit;
        if (visits.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visits not found");
        }
        for (Iterator<Visit> iterator = visits.iterator(); iterator.hasNext(); ) {
            visit = iterator.next();
            if (visit.getDoctor().getId() == doctorId) {
                PatientDto patientDto = PatientDto.builder()
                        .firstName(visit.getPatient().getFirstName())
                        .lastName(visit.getPatient().getLastName())
                        .middleName(visit.getPatient().getMiddleName())
                        .dateOfVisit(visit.getDateOfVisit())
                        .build();
                result.add(patientDto);
            }
        }
        return result;
    }

    @Override
    public List<DoctorDto> getAllPatientVisits(long patientId) {
        List<Visit> visits = visitRepository.findAll();
        List<DoctorDto> result = new ArrayList<>();
        Visit visit;
        if (visits.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visits not found");
        }
        for (Iterator<Visit> iterator = visits.iterator(); iterator.hasNext(); ) {
            visit = iterator.next();
            if (visit.getPatient().getId() == patientId) {
                DoctorDto doctorDto = DoctorDto.builder()
                        .firstName(visit.getDoctor().getFirstName())
                        .lastName(visit.getDoctor().getLastName())
                        .middleName(visit.getDoctor().getMiddleName())
                        .position(visit.getDoctor().getPosition())
                        .dateOfVisit(visit.getDateOfVisit())
                        .build();
                result.add(doctorDto);
            }
        }
        return result;
    }

    @Override
    public void deleteVisit(long id) {
        if (!visitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Visit not found");
        }
        visitRepository.deleteById(id);
    }

}
