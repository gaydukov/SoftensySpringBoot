package com.softensy.SoftensySpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String middleName;
    @Column
    private long doctorId;
    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date dateOfBirth;
    @Column
    private long phoneNamber;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String middleName, long doctorId, Date dateOfBirth, long phoneNamber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.doctorId = doctorId;
        this.dateOfBirth = dateOfBirth;
        this.phoneNamber = phoneNamber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getPhoneNamber() {
        return phoneNamber;
    }

    public void setPhoneNamber(long phoneNamber) {
        this.phoneNamber = phoneNamber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", doctorId=" + doctorId +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNamber=" + phoneNamber +
                '}';
    }
}
