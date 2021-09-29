package com.softensy.SoftensySpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Role {
    DOCTOR(Set.of(Permission.APPOINTMENT_WRITE, Permission.APPOINTMENT_READ
            , Permission.DOCTOR_WRITE, Permission.DOCTOR_READ)),
    PATIENT(Set.of(Permission.APPOINTMENT_WRITE, Permission.APPOINTMENT_READ
            , Permission.PATIENT_WRITE, Permission.PATIENT_READ)),
    ADMIN(Set.of(Permission.APPOINTMENT_WRITE, Permission.APPOINTMENT_READ
            , Permission.DOCTOR_WRITE, Permission.DOCTOR_READ
            , Permission.PATIENT_WRITE, Permission.PATIENT_READ, Permission.ADMIN_WRITE));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
