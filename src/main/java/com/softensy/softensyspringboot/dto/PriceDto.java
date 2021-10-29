package com.softensy.softensyspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceDto {
    private String position;
    private float primaryPrice;
    private float secondaryPrice;

}
