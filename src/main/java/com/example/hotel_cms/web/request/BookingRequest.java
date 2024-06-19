package com.example.hotel_cms.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private Date checkInDate;
    private Date checkOutDate;
    private Long roomId;
    private Long userId;

}
