package com.example.hotel_cms.service;

import com.example.hotel_cms.model.Booking;
import com.example.hotel_cms.web.response.BookingResponse;

import java.util.List;

public interface BookingService {

    List<Booking> findAll();
    Booking findById(Long id);
    Booking create(Booking booking, Long roomId, Long userId);
    Booking update(Booking booking);
    void delete(Long id);

    List<BookingResponse> getAllBookings();
}
