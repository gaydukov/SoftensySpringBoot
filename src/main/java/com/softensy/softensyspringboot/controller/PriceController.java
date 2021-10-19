package com.softensy.softensyspringboot.controller;

import com.softensy.softensyspringboot.dto.PriceDto;
import com.softensy.softensyspringboot.entity.Price;
import com.softensy.softensyspringboot.mapper.PriceMapper;
import com.softensy.softensyspringboot.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/price")
public class PriceController {
    private final PriceService priceService;
    private final PriceMapper priceMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Price> savePrice(@RequestBody PriceDto priceDto) {
        Price result = priceService.savePrice(priceMapper.dtoToEntity(priceDto));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Price> updatePrice(@RequestBody PriceDto priceDto) {
        return new ResponseEntity<>(priceService.updatePrice(priceMapper.dtoToEntity(priceDto)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('doctor:read') or hasAuthority('admin:read') or hasAuthority('patient:read')")
    public ResponseEntity<List<Price>> getAllPrice() {
        return new ResponseEntity<>(priceService.getAllPrice(), HttpStatus.OK);
    }

}
