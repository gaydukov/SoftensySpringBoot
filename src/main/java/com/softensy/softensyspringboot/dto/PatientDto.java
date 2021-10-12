package com.softensy.softensyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {
    private String firstName;
    private String lastName;
    private String middleName;

}
