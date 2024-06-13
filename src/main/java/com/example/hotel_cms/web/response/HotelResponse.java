package com.example.hotel_cms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
    private Long id;
    private String name;
    private String headline;
    private String city;
    private String address;
    private Double centerDistance;
    private Integer rating;
    private Integer reviewCount;
}
