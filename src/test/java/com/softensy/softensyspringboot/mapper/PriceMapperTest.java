package com.softensy.softensyspringboot.mapper;

import com.softensy.softensyspringboot.dto.PriceDto;
import com.softensy.softensyspringboot.entity.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstPrice;
import static com.softensy.softensyspringboot.TestDataGenerator.getFirstPriceDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PriceMapperTest {
    @Autowired
    private PriceMapper priceMapper;

    @Test
    @DisplayName("check mapping price entity to dto")
    void testMappingPriceEntityToDto() {
        // given
        Price price = getFirstPrice();
        PriceDto expectedPriceDto = getFirstPriceDto();
        // when
        PriceDto actualPriceDto = priceMapper.entityToDto(price);
        //then
        assertEquals(expectedPriceDto.getPosition(), actualPriceDto.getPosition());
        assertEquals(expectedPriceDto.getPrimaryPrice(), actualPriceDto.getPrimaryPrice());
        assertEquals(expectedPriceDto.getSecondaryPrice(), actualPriceDto.getSecondaryPrice());
        assertEquals(expectedPriceDto, actualPriceDto);
        assertNotNull(expectedPriceDto);
        assertNotNull(actualPriceDto);
    }

    @Test
    @DisplayName("check mapping price dto to entity")
    void testMappingPriceDtoToEntity() {
        // given
        Price expectedPrice = getFirstPrice();
        PriceDto priceDto = getFirstPriceDto();
        // when
        Price actualPrice = priceMapper.dtoToEntity(priceDto);
        //then
        assertEquals(expectedPrice.getPosition(), actualPrice.getPosition());
        assertEquals(expectedPrice.getPrimaryPrice(), actualPrice.getPrimaryPrice());
        assertEquals(expectedPrice.getSecondaryPrice(), actualPrice.getSecondaryPrice());
        assertEquals(expectedPrice, actualPrice);
        assertNotNull(expectedPrice);
        assertNotNull(actualPrice);
    }

}
