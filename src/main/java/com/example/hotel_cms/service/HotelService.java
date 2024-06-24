package com.example.hotel_cms.service;

import com.example.hotel_cms.model.Hotel;
import com.example.hotel_cms.web.filter.HotelFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    Hotel create(Hotel hotel);
    Hotel update(Long id,Hotel hotel);
    Hotel findById(Long id);
    List<Hotel> findAll(HotelFilter filter);
    List<Hotel> findByFilter(HotelFilter filter);
    void deleteById(Long id);
}
