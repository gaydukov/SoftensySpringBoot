package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.PriceDto;
import com.softensy.softensyspringboot.entity.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    public PriceDto entityToDto(Price price) {
        return PriceDto.builder()
                .position(price.getPosition())
                .primaryPrice(price.getPrimaryPrice())
                .secondaryPrice(price.getSecondaryPrice())
                .build();
    }

    public Price dtoToEntity(PriceDto price) {
        return Price.builder()
                .position(price.getPosition())
                .primaryPrice(price.getPrimaryPrice())
                .secondaryPrice(price.getSecondaryPrice())
                .build();
    }

}
