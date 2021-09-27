package com.softensy.SoftensySpringBoot.entity;


public enum Permission {
    DOCTOR_READ("doctor:read"),
    DOCTOR_WRITE("doctor:write"),
    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write"),
    ADMIN_WRITE("admin:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
