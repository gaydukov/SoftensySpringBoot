package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.entity.Price;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.PriceRepository;
import com.softensy.softensyspringboot.service.PriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.softensy.softensyspringboot.TestDataGenerator.getFirstPrice;
import static com.softensy.softensyspringboot.TestDataGenerator.getPricerList;
import static org.mockito.Mockito.*;

@SpringBootTest
class PriceServiceImplTest {
    @Autowired
    private PriceService priceService;
    @MockBean
    private PriceRepository priceRepository;

    @Test
    @DisplayName("checking get all prices")
    void testGetAllPricesReturnAllPricesAndVerifyFindAll() {
        // given
        List<Price> expectedPrices = getPricerList();
        // when
        when(priceRepository.findAll()).thenReturn(expectedPrices);
        List<Price> actualPrices = priceService.getAllPrice();
        //then
        Assertions.assertEquals(expectedPrices, actualPrices);
        Assertions.assertNotNull(expectedPrices.get(0));
        Assertions.assertNotNull(expectedPrices.get(1));
        Assertions.assertNotNull(expectedPrices.get(2));
        verify(priceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking get all prices with empty list")
    void testGetAllPricesWithEmptyListReturnException() {
        // when
        when(priceRepository.findAll()).thenThrow(NotFoundException.class);
        //then
        Assertions.assertThrows(NotFoundException.class, () -> priceService.getAllPrice());
        verify(priceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("checking save price")
    void testAddPriceReturnPriceAndVerifyPriceSave() {
        // given
        Price expectedPrice = getFirstPrice();
        // when
        when(priceRepository.save(any(Price.class))).thenReturn(expectedPrice);
        Price actualPrice = priceService.savePrice(expectedPrice);
        //then
        Assertions.assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).save(any(Price.class));
    }

    @Test
    @DisplayName("checking save invalid price, expected exception")
    void testAddInvalidPriceReturnException() {
        Assertions.assertThrows(NotFoundException.class, () -> priceService.savePrice(null));
    }

    @Test
    @DisplayName("checking update price")
    void testUpdatePriceReturnPriceAndVerifyPriceUpdate() {
        // given
        Price expectedPrice = getFirstPrice();
        // when
        when(priceRepository.save(any(Price.class))).thenReturn(expectedPrice);
        Price actualPrice = priceService.updatePrice(expectedPrice);
        //then
        Assertions.assertEquals(expectedPrice, actualPrice);
        verify(priceRepository, times(1)).save(any(Price.class));
    }

    @Test
    @DisplayName("checking update invalid price, expected exception")
    void testUpdateInvalidDoctorReturnException() {
        Assertions.assertThrows(NotFoundException.class, () -> priceService.updatePrice(null));
    }

}
