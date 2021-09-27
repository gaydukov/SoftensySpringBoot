package com.softensy.SoftensySpringBoot.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
    DOCTOR(Set.of(Permission.APPOINTMENT_WRITE, Permission.APPOINTMENT_READ
            , Permission.DOCTOR_WRITE, Permission.DOCTOR_READ)),
    PATIENT(Set.of(Permission.APPOINTMENT_WRITE, Permission.APPOINTMENT_READ
            , Permission.PATIENT_WRITE, Permission.PATIENT_READ)),
    ADMIN(Set.of(Permission.APPOINTMENT_WRITE, Permission.APPOINTMENT_READ
            , Permission.DOCTOR_WRITE, Permission.DOCTOR_READ
            , Permission.PATIENT_WRITE, Permission.PATIENT_READ, Permission.ADMIN_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
