package com.softensy.SoftensySpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSecurity extends UserSecurity {
    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
