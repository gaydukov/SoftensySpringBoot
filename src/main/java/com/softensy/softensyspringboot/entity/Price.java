package com.softensy.softensyspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String position;
    private float primaryPrice;
    private float secondaryPrice;

}
