package com.example.hotel_cms.service.impl;

import com.example.hotel_cms.exception.BookedRoomException;
import com.example.hotel_cms.exception.EntityNotFoundException;
import com.example.hotel_cms.mapping.BookingMapper;
import com.example.hotel_cms.model.Booking;
import com.example.hotel_cms.model.Room;
import com.example.hotel_cms.model.User;
import com.example.hotel_cms.repository.BookingRepository;
import com.example.hotel_cms.service.BookingService;
import com.example.hotel_cms.service.RoomService;
import com.example.hotel_cms.service.UserService;
import com.example.hotel_cms.web.response.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public Booking create(Booking booking, Long roomId, Long userId) {

        Room room = roomService.findById(roomId);
        User user = userService.findById(userId);
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        bookings.forEach(b -> {
            if (isOverLap(b.getCheckInDate(),b.getCheckOutDate(),booking.getCheckInDate(),booking.getCheckOutDate()))
                throw new BookedRoomException("Room is not available for the selected dates");
        });
        booking.setRoom(room);
        booking.setUser(user);
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingMapper::bookingToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                MessageFormat.format("booking with id {0} not found", id)));
    }

    @Override
    public Booking update(Booking booking) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private boolean isOverLap(Date start1, Date end1, Date start2, Date end2){
        if ((start2.before(start1) && end2.before(start1)) || (start2.after(end1)&& end2.after(end1)))
            return false;
        else return true;
    }
}
