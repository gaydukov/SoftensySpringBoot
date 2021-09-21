package com.softensy.SoftensySpringBoot;

import com.softensy.SoftensySpringBoot.dto.*;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TestDataGeneration {

    public static Doctor getFirstDoctor() {
        return Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
    }

    public static Doctor getSecondDoctor() {
        return Doctor.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .position("Terapevt")
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
    }

    public static Doctor getThirdDoctor() {
        return Doctor.builder()
                .id(3)
                .firstName("Semen")
                .lastName("Semenov")
                .middleName("Ivanovich")
                .position("Okulist")
                .dateOfBirth(LocalDate.of(1987, 1, 25))
                .phoneNumber("45632")
                .build();
    }

    public static DoctorDto getDoctorDto(Doctor doctor) {
        return DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
    }

    public static List<Doctor> getDoctorList() {
        List<Doctor> result = new ArrayList<>();
        result.add(getFirstDoctor());
        result.add(getSecondDoctor());
        result.add(getThirdDoctor());
        return result;
    }

    public static List<DoctorDto> getDoctorDtoList() {
        List<DoctorDto> result = new ArrayList<>();
        result.add(getDoctorDto(getFirstDoctor()));
        result.add(getDoctorDto(getSecondDoctor()));
        result.add(getDoctorDto(getThirdDoctor()));
        return result;
    }

    public static Patient getFirstPatient() {
        return Patient.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("12345")
                .build();
    }

    public static Patient getSecondPatient() {
        return Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
    }

    public static Patient getThirdPatient() {
        return Patient.builder()
                .id(3)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("12345")
                .build();
    }

    public static PatientDto getPatientDto(Patient patient) {
        return PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
    }

    public static List<Patient> getPatientList() {
        List<Patient> result = new ArrayList<>();
        result.add(getFirstPatient());
        result.add(getSecondPatient());
        result.add(getThirdPatient());
        return result;
    }

    public static List<PatientDto> getPatientDtoList() {
        List<PatientDto> result = new ArrayList<>();
        result.add(getPatientDto(getFirstPatient()));
        result.add(getPatientDto(getSecondPatient()));
        result.add(getPatientDto(getThirdPatient()));
        return result;
    }

    public static List<LocalDateTime> getAppointmentDateList() {
        List<LocalDateTime> result = new ArrayList<>();
        result.add(LocalDateTime.of(2021, 11, 1, 9, 30));
        result.add(LocalDateTime.of(2021, 11, 2, 10, 30));
        result.add(LocalDateTime.of(2021, 11, 4, 11, 30));
        return result;
    }

    public static Appointment getFirstAppointment() {
        return Appointment.builder()
                .id(1)
                .doctor(getFirstDoctor())
                .patient(getFirstPatient())
                .appointmentDate(getAppointmentDateList().get(0))
                .build();
    }

    public static Appointment getSecondAppointment() {
        return Appointment.builder()
                .id(2)
                .doctor(getSecondDoctor())
                .patient(getSecondPatient())
                .appointmentDate(getAppointmentDateList().get(1))
                .build();
    }

    public static Appointment getThirdAppointment() {
        return Appointment.builder()
                .id(3)
                .doctor(getThirdDoctor())
                .patient(getThirdPatient())
                .appointmentDate(getAppointmentDateList().get(2))
                .build();
    }

    public static List<Appointment> getAppointmentList() {
        List<Appointment> result = new ArrayList<>();
        result.add(getFirstAppointment());
        result.add(getSecondAppointment());
        result.add(getThirdAppointment());
        return result;
    }

    public static AppointmentDto getAppointmentDto(Appointment appointment) {
        return AppointmentDto.builder()
                .patientId(appointment.getPatient().getId())
                .doctorId(appointment.getDoctor().getId())
                .appointmentDate(appointment.getAppointmentDate())
                .build();
    }

    public static List<AppointmentDto> getAppointmentDtoList() {
        List<AppointmentDto> result = new ArrayList<>();
        result.add(getAppointmentDto(getFirstAppointment()));
        result.add(getAppointmentDto(getSecondAppointment()));
        result.add(getAppointmentDto(getThirdAppointment()));
        return result;
    }

    public static DoctorAppointmentDto getFirstDoctorAppointmentDto() {
        return DoctorAppointmentDto.builder()
                .patientDto(getPatientDto(getFirstPatient()))
                .appointmentDate(getAppointmentDateList().get(0))
                .build();
    }

    public static DoctorAppointmentDto getSecondDoctorAppointmentDto() {
        return DoctorAppointmentDto.builder()
                .patientDto(getPatientDto(getSecondPatient()))
                .appointmentDate(getAppointmentDateList().get(1))
                .build();
    }

    public static DoctorAppointmentDto getThirdDoctorAppointmentDto() {
        return DoctorAppointmentDto.builder()
                .patientDto(getPatientDto(getThirdPatient()))
                .appointmentDate(getAppointmentDateList().get(2))
                .build();
    }

    public static List<DoctorAppointmentDto> getDoctorAppointmentDtoList() {
        List<DoctorAppointmentDto> result = new ArrayList<>();
        result.add(getFirstDoctorAppointmentDto());
        result.add(getSecondDoctorAppointmentDto());
        result.add(getThirdDoctorAppointmentDto());
        return result;
    }

    public static PatientAppointmentDto getFirstPatientAppointmentDto() {
        return PatientAppointmentDto.builder()
                .doctorDto(getDoctorDto(getFirstDoctor()))
                .appointmentDate(getAppointmentDateList().get(0))
                .build();
    }

    public static PatientAppointmentDto getSecondPatientAppointmentDto() {
        return PatientAppointmentDto.builder()
                .doctorDto(getDoctorDto(getSecondDoctor()))
                .appointmentDate(getAppointmentDateList().get(1))
                .build();
    }

    public static PatientAppointmentDto getThirdPatientAppointmentDto() {
        return PatientAppointmentDto.builder()
                .doctorDto(getDoctorDto(getThirdDoctor()))
                .appointmentDate(getAppointmentDateList().get(2))
                .build();
    }

    public static List<PatientAppointmentDto> getPatientAppointmentDtoList() {
        List<PatientAppointmentDto> result = new ArrayList<>();
        result.add(getFirstPatientAppointmentDto());
        result.add(getSecondPatientAppointmentDto());
        result.add(getThirdPatientAppointmentDto());
        return result;
    }

}
