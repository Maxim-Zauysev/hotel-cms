package com.example.hotel_cms.web.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomFilter {
    private Long id;
    private String name;
    private Double minCost;
    private Double maxCost;
    private Integer guestsLimit;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;
    private Long hotelId;
    private Integer pageNumber;
    private Integer pageSize;
}
