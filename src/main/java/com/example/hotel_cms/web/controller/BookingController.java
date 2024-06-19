package com.example.hotel_cms.web.controller;

import com.example.hotel_cms.mapping.BookingMapper;
import com.example.hotel_cms.model.Booking;
import com.example.hotel_cms.service.BookingService;
import com.example.hotel_cms.web.request.BookingRequest;
import com.example.hotel_cms.web.response.BookingResponse;
import com.example.hotel_cms.web.response.BookingResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request){
        Booking booking = bookingService.create(bookingMapper.requestToBooking(request),request.getRoomId(),request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingMapper.bookingToResponse(booking));
    }

    @GetMapping
    public ResponseEntity<BookingResponseList> getAll(){

        return ResponseEntity.ok(new BookingResponseList(bookingService.getAllBookings()));
    }


}
