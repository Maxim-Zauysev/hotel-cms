package com.example.hotel_cms.web.response;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private Date checkInDate;
    private Date checkOutDate;
    private RoomResponse room;
    private UserResponse user;

}
