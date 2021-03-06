package com.softensy.softensyspringboot;

import com.softensy.softensyspringboot.dto.*;
import com.softensy.softensyspringboot.entity.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class TestDataGenerator {

    public static Price getFirstPrice() {
        return Price.builder()
                .position("family doctor")
                .primaryPrice(300)
                .secondaryPrice(1000)
                .build();
    }

    public static Price getSecondPrice() {
        return Price.builder()
                .position("ENT")
                .primaryPrice(400)
                .secondaryPrice(1200)
                .build();
    }

    public static Price getThirdPrice() {
        return Price.builder()
                .position("surgeon")
                .primaryPrice(500)
                .secondaryPrice(1500)
                .build();
    }

    public static List<Price> getPricerList() {
        List<Price> result = new ArrayList<>();
        result.add(getFirstPrice());
        result.add(getSecondPrice());
        result.add(getThirdPrice());
        return result;
    }

    public static PriceDto getFirstPriceDto() {
        return PriceDto.builder()
                .position("family doctor")
                .primaryPrice(300)
                .secondaryPrice(1000)
                .build();
    }

    public static PriceDto getSecondPriceDto() {
        return PriceDto.builder()
                .position("ENT")
                .primaryPrice(400)
                .secondaryPrice(1200)
                .build();
    }

    public static PriceDto getThirdPriceDto() {
        return PriceDto.builder()
                .position("surgeon")
                .primaryPrice(500)
                .secondaryPrice(1500)
                .build();
    }

    public static Doctor getFirstDoctor() {
        return Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position(getFirstPrice())
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
                .position(getSecondPrice())
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
                .position(getThirdPrice())
                .dateOfBirth(LocalDate.of(1987, 1, 25))
                .phoneNumber("45632")
                .build();
    }

    public static DoctorDto getDoctorDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition().getPosition())
                .dateOfBirth(doctor.getDateOfBirth())
                .phoneNumber(doctor.getPhoneNumber())
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
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .doctorId(patient.getDoctorId())
                .dateOfBirth(patient.getDateOfBirth())
                .phoneNumber(patient.getPhoneNumber())
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

    private static Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authority = new HashSet<>();
        authority.add(new SimpleGrantedAuthority("appointment:read"));
        authority.add(new SimpleGrantedAuthority("appointment:write"));
        authority.add(new SimpleGrantedAuthority("patient:read"));
        authority.add(new SimpleGrantedAuthority("patient:write"));
        return authority;
    }

    public static UserDetails getUserDetails() {
        return User.builder()
                .username("user")
                .password("user")
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities(getAuthorities())
                .build();
    }

    public static UserSecurity getPatientSecurity() {
        return UserSecurity.builder()
                .login("user")
                .password("user")
                .role(Role.PATIENT)
                .accountType(AccountType.PATIENT)
                .userId(1L)
                .build();
    }

    public static UserSecurity getDoctorSecurity() {
        return UserSecurity.builder()
                .login("user")
                .password("user")
                .role(Role.DOCTOR)
                .accountType(AccountType.DOCTOR)
                .userId(1L)
                .build();
    }

    public static AbsenceSchedule getFirstAbsenceSchedule() {
        return AbsenceSchedule.builder()
                .id(1L)
                .startSickLeave(LocalDateTime.of(2021, 11, 1, 0, 0))
                .endSickLeave(LocalDateTime.of(2021, 11, 14, 0, 0))
                .startVocation(LocalDateTime.of(2021, 12, 1, 0, 0))
                .endVocation(LocalDateTime.of(2021, 12, 21, 0, 0))
                .doctor(getFirstDoctor())
                .build();
    }

    public static AbsenceSchedule getSecondAbsenceSchedule() {
        return AbsenceSchedule.builder()
                .id(2L)
                .startSickLeave(LocalDateTime.of(2021, 11, 15, 0, 0))
                .endSickLeave(LocalDateTime.of(2021, 11, 21, 0, 0))
                .startVocation(LocalDateTime.of(2021, 12, 14, 0, 0))
                .endVocation(LocalDateTime.of(2021, 12, 25, 0, 0))
                .doctor(getSecondDoctor())
                .build();
    }

    public static AbsenceSchedule getThirdAbsenceSchedule() {
        return AbsenceSchedule.builder()
                .id(3L)
                .startSickLeave(LocalDateTime.of(2021, 12, 1, 0, 0))
                .endSickLeave(LocalDateTime.of(2021, 12, 7, 0, 0))
                .startVocation(LocalDateTime.of(2021, 12, 20, 0, 0))
                .endVocation(LocalDateTime.of(2021, 12, 31, 0, 0))
                .doctor(getThirdDoctor())
                .build();
    }

    public static AbsenceScheduleDto getFirstAbsenceScheduleDto() {
        return AbsenceScheduleDto.builder()
                .id(1L)
                .startSickLeave(LocalDateTime.of(2021, 11, 1, 0, 0))
                .endSickLeave(LocalDateTime.of(2021, 11, 14, 0, 0))
                .startVocation(LocalDateTime.of(2021, 12, 1, 0, 0))
                .endVocation(LocalDateTime.of(2021, 12, 21, 0, 0))
                .doctorId(1L)
                .build();
    }

    public static AbsenceScheduleDto getSecondAbsenceScheduleDto() {
        return AbsenceScheduleDto.builder()
                .id(2L)
                .startSickLeave(LocalDateTime.of(2021, 11, 15, 0, 0))
                .endSickLeave(LocalDateTime.of(2021, 11, 21, 0, 0))
                .startVocation(LocalDateTime.of(2021, 12, 14, 0, 0))
                .endVocation(LocalDateTime.of(2021, 12, 25, 0, 0))
                .doctorId(2L)
                .build();
    }

    public static AbsenceScheduleDto getThirdAbsenceScheduleDto() {
        return AbsenceScheduleDto.builder()
                .id(3L)
                .startSickLeave(LocalDateTime.of(2021, 12, 1, 0, 0))
                .endSickLeave(LocalDateTime.of(2021, 12, 7, 0, 0))
                .startVocation(LocalDateTime.of(2021, 12, 20, 0, 0))
                .endVocation(LocalDateTime.of(2021, 12, 31, 0, 0))
                .doctorId(3L)
                .build();
    }

    public static List<AbsenceScheduleDto> getAbsenceScheduleListDto() {
        List<AbsenceScheduleDto> result = new ArrayList<>();
        result.add(getFirstAbsenceScheduleDto());
        result.add(getSecondAbsenceScheduleDto());
        result.add(getThirdAbsenceScheduleDto());
        return result;
    }

    public static List<AbsenceSchedule> getAbsenceScheduleList() {
        List<AbsenceSchedule> result = new ArrayList<>();
        result.add(getFirstAbsenceSchedule());
        result.add(getSecondAbsenceSchedule());
        result.add(getThirdAbsenceSchedule());
        return result;
    }

    public static AbsenceScheduleDto getInvalidAbsenceSchedule() {
        return AbsenceScheduleDto.builder()
                .id(4L)
                .doctorId(1L)
                .startSickLeave(null)
                .endSickLeave(null)
                .startVocation(null)
                .endVocation(null)
                .build();
    }

}
