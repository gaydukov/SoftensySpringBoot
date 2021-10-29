package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.entity.Price;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.PriceRepository;
import com.softensy.softensyspringboot.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;

    @Override
    public List<Price> getAllPrice() {
        List<Price> priceList = priceRepository.findAll();
        if (priceList.isEmpty()) {
            throw new NotFoundException("List price is empty");
        }
        return priceList;
    }

    @Override
    public Price savePrice(Price price) {
        if (price == null) {
            throw new NotFoundException("Price not found");
        }
        return priceRepository.save(price);
    }

    @Override
    public Price updatePrice(Price price) {
        if (price == null) {
            throw new NotFoundException("Price not found");
        }
        return priceRepository.saveAndFlush(price);
    }

}
