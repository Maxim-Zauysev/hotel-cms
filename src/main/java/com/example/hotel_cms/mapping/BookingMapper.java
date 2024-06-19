package com.example.hotel_cms.mapping;

import com.example.hotel_cms.model.Booking;
import com.example.hotel_cms.web.request.BookingRequest;
import com.example.hotel_cms.web.response.BookingResponse;
import com.example.hotel_cms.web.response.BookingResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, RoomMapper.class})
public interface BookingMapper {

    Booking requestToBooking(BookingRequest request);
    BookingResponse bookingToResponse(Booking booking);

    default BookingResponseList bookingListToBookingResponseList(List<Booking> bookings) {
        List<BookingResponse> bookingResponses =
                bookings.stream().map(this::bookingToResponse).collect(Collectors.toList());

        BookingResponseList response = new BookingResponseList(bookingResponses);
        return response;
    }


}
