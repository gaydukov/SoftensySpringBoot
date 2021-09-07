package com.softensy.SoftensySpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @NonNull
    private String firstName;
    @Column
    @NonNull
    private String lastName;
    @Column
    @NonNull
    private String middleName;
    @Column
    @NonNull
    private long doctorId;
    @Column
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    @Column
    @NonNull
    private long phoneNumber;
}
