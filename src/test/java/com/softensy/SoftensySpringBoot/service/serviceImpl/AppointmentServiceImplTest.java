package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceImplTest {
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("checking create new appointment and saves it")
    void whenCreateNewAppointmentThenReturnAppointmentAndVerifyAppointmentSave() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment actualAppointment = new Appointment(1, patient, doctor, dateOfAppointment);
        // when
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(actualAppointment);
        Appointment expectedAppointment = appointmentService.createAppointment(actualAppointment);
        //then
        Assertions.assertEquals(expectedAppointment, actualAppointment);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(appointmentRepository).save(any(Appointment.class));
        Assertions.assertNotNull(doctor);
        Assertions.assertNotNull(patient);
        Assertions.assertNotNull(actualAppointment);
    }

    @Test
    @DisplayName("checking all appointments to doctor by doctor Id")
    void whenGetAllAppointmentsToDoctorByDoctorIdReturnListPatientDtoWhoAppointmentThisDoctor() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfFirstAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondAppointment = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdAppointment = LocalDateTime.of(2021, 11, 4, 11, 30);
        Appointment firstAppointment = new Appointment(1, patient, doctor, dateOfFirstAppointment);
        Appointment secondAppointment = new Appointment(2, patient, doctor, dateOfSecondAppointment);
        Appointment thirdAppointment = new Appointment(3, patient, doctor, dateOfThirdAppointment);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(firstAppointment);
        appointmentList.add(secondAppointment);
        appointmentList.add(thirdAppointment);
        PatientDto actualFirstPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfAppointment(dateOfFirstAppointment)
                .build();
        PatientDto actualSecondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfAppointment(dateOfSecondAppointment)
                .build();
        PatientDto actualThirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .dateOfAppointment(dateOfThirdAppointment)
                .build();
        List<PatientDto> actualPatientList = new ArrayList<>();
        actualPatientList.add(actualFirstPatient);
        actualPatientList.add(actualSecondPatient);
        actualPatientList.add(actualThirdPatient);
        // when
        when(appointmentRepository.findAll()).thenReturn(appointmentList);
        List<PatientDto> expectedPatientList = appointmentService.getAllAppointmentsToDoctor(1L);
        //then
        Assertions.assertEquals(expectedPatientList, actualPatientList);
        Assertions.assertNotNull(expectedPatientList);
        Assertions.assertNotNull(actualPatientList);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking all patient's appointments to doctor by patient Id")
    void whenGetAllPatientAppointmentsByPatientIdReturnListDoctorDtoWhichThisPatientIsAssigned() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfFirstAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondAppointment = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdAppointment = LocalDateTime.of(2021, 11, 4, 11, 30);
        Appointment firstAppointment = new Appointment(1, patient, doctor, dateOfFirstAppointment);
        Appointment secondAppointment = new Appointment(2, patient, doctor, dateOfSecondAppointment);
        Appointment thirdAppointment = new Appointment(3, patient, doctor, dateOfThirdAppointment);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(firstAppointment);
        appointmentList.add(secondAppointment);
        appointmentList.add(thirdAppointment);
        DoctorDto actualFirstDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfAppointment(dateOfFirstAppointment)
                .build();
        DoctorDto actualSecondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfAppointment(dateOfSecondAppointment)
                .build();
        DoctorDto actualThirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .dateOfAppointment(dateOfThirdAppointment)
                .build();
        List<DoctorDto> actualDoctorList = new ArrayList<>();
        actualDoctorList.add(actualFirstDoctor);
        actualDoctorList.add(actualSecondDoctor);
        actualDoctorList.add(actualThirdDoctor);
        // when
        when(appointmentRepository.findAll()).thenReturn(appointmentList);
        List<DoctorDto> expectedDoctorList = appointmentService.getAllPatientAppointments(2L);
        //then
        Assertions.assertEquals(expectedDoctorList, actualDoctorList);
        Assertions.assertNotNull(expectedDoctorList);
        Assertions.assertNotNull(actualDoctorList);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking get by id appointment and delete him")
    void whenDeleteAppointmentByIdThenVerifyAppointmentDelete() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime dateOfAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment appointment = new Appointment(1, patient, doctor, dateOfAppointment);
        // when
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        appointmentRepository.deleteById(1L);
        appointmentService.deleteAppointment(1L);
        //then
        Assertions.assertNotNull(appointment);
        verify(appointmentRepository, times(2)).deleteById(appointment.getId());
    }

}
