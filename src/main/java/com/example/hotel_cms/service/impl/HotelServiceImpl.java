package com.example.hotel_cms.service.impl;

import com.example.hotel_cms.exception.EntityNotFoundException;
import com.example.hotel_cms.model.Hotel;
import com.example.hotel_cms.repository.HotelRepository;
import com.example.hotel_cms.service.HotelService;
import com.example.hotel_cms.utility.BeanUtils;
import com.example.hotel_cms.web.filter.HotelFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Long id,Hotel hotel) {
        Hotel hotelToUpdate = findById(id);
        BeanUtils.copyProperties(hotelToUpdate,hotel);
        return hotelRepository.save(hotelToUpdate);
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                MessageFormat.format("hotel with id {0} not found", id)));
    }

    @Override
    public List<Hotel> findAll(HotelFilter filter) {
        return hotelRepository.findAll(PageRequest.of(
                filter.getPageNumber(),
                filter.getPageSize()
        )).getContent();
    }

    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }
}
