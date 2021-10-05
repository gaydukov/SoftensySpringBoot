package com.softensy.SoftensySpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    DOCTOR_READ("doctor:read"),
    DOCTOR_WRITE("doctor:write"),
    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write"),
    ADMIN_WRITE("admin:write"),
    ADMIN_READ("admin:read");

    private final String permission;

}
