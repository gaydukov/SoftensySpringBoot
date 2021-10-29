package com.softensy.softensyspringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softensy.softensyspringboot.dto.PriceDto;
import com.softensy.softensyspringboot.entity.Price;
import com.softensy.softensyspringboot.mapper.PriceMapper;
import com.softensy.softensyspringboot.service.PriceService;
import com.softensy.softensyspringboot.service.serviceimpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PriceController.class)
@ContextConfiguration()
class PriceControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PriceMapper priceMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private PriceService priceService;

    @Test
    @DisplayName("checking save price with authenticated admin with status 201")
    @WithMockUser(authorities = "admin:write")
    void testCreatePriceWithAuthenticatedReturnStatus201AndPrice() throws Exception {
        //given
        Price price = getFirstPrice();
        PriceDto priceDto = getFirstPriceDto();
        //when
        when(priceMapper.dtoToEntity(priceDto)).thenReturn(price);
        when(priceService.savePrice(price)).thenReturn(price);
        //then
        mockMvc.perform(post("/price")
                        .content(objectMapper.writeValueAsString(priceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.position").value("family doctor"))
                .andExpect(jsonPath("$.primaryPrice").value(300.0))
                .andExpect(jsonPath("$.secondaryPrice").value(1000.0))
                .andExpect(content().json(objectMapper.writeValueAsString(priceDto)));
    }

    @Test
    @DisplayName("checking save price with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testCreatePriceWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Price price = getFirstPrice();
        PriceDto priceDto = getFirstPriceDto();
        //when
        when(priceMapper.dtoToEntity(priceDto)).thenReturn(price);
        when(priceService.savePrice(price)).thenReturn(price);
        //then
        mockMvc.perform(post("/price")
                        .content(objectMapper.writeValueAsString(priceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking update with authenticated admin with status 201")
    @WithMockUser(authorities = "admin:write")
    void testUpdatePriceWithAuthenticatedAdminReturnStatus200AndPrice() throws Exception {
        //given
        Price price = getFirstPrice();
        PriceDto priceDto = getFirstPriceDto();
        //when
        when(priceMapper.dtoToEntity(priceDto)).thenReturn(price);
        when(priceService.updatePrice(price)).thenReturn(price);
        //then
        mockMvc.perform(put("/price")
                        .content(objectMapper.writeValueAsString(priceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value("family doctor"))
                .andExpect(jsonPath("$.primaryPrice").value(300.0))
                .andExpect(jsonPath("$.secondaryPrice").value(1000.0))
                .andExpect(content().json(objectMapper.writeValueAsString(priceDto)));
    }

    @Test
    @DisplayName("checking update price with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testUpdatePriceWithAnotherAuthenticatedReturnStatus403() throws Exception {
        //given
        Price price = getFirstPrice();
        PriceDto priceDto = getFirstPriceDto();
        //when
        when(priceMapper.dtoToEntity(priceDto)).thenReturn(price);
        when(priceService.updatePrice(price)).thenReturn(price);
        //then
        mockMvc.perform(put("/price")
                        .content(objectMapper.writeValueAsString(priceDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("checking get all price with authenticated with status 201")
    @WithMockUser(authorities = "admin:read")
    void testGetPriceListWithAuthenticatedReturnStatus200AndPriceList() throws Exception {
        // given
        List<Price> priceList = getPricerList();
        //when
        when(priceService.getAllPrice()).thenReturn(priceList);
        //then
        mockMvc.perform(get("/price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.
                        asList(priceList.get(0), priceList.get(1), priceList.get(2)))));
        for (int index = 0; index < priceList.size(); index++) {
            mockMvc.perform(get("/price"))
                    .andExpect(jsonPath("$[" + index + "].position")
                            .value(priceList.get(index).getPosition()))
                    .andExpect(jsonPath("$[" + index + "].primaryPrice")
                            .value(priceList.get(index).getPrimaryPrice()))
                    .andExpect(jsonPath("$[" + index + "].secondaryPrice")
                            .value(priceList.get(index).getSecondaryPrice()));
        }
    }

    @Test
    @DisplayName("checking get all price with another authenticated with status 403")
    @WithMockUser(authorities = "doctor:write")
    void testGetPriceListWithAnotherAuthenticatedReturnStatus403() throws Exception {
        // given
        List<Price> priceList = getPricerList();
        //when
        when(priceService.getAllPrice()).thenReturn(priceList);
        //then
        mockMvc.perform(get("/price"))
                .andExpect(status().isForbidden());
    }

}
