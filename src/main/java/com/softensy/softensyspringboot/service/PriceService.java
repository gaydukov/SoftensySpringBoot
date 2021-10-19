package com.softensy.softensyspringboot.service;

import com.softensy.softensyspringboot.entity.Price;

import java.util.List;

public interface PriceService {

    List<Price> getAllPrice();

    Price savePrice(Price price);

    Price updatePrice(Price price);

}
