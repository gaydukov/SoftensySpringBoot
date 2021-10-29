package com.softensy.softensyspringboot.repository;

import com.softensy.softensyspringboot.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, String> {
}
