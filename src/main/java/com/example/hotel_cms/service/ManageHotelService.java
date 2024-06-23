package com.example.hotel_cms.service;

import com.example.hotel_cms.model.Hotel;

public interface ManageHotelService {
    Hotel updateRating(Long hotelId, Integer newMark);
}
