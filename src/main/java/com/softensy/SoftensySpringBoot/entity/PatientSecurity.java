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
public class PatientSecurity extends UserSecurity {
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

}
