package com.example.hotel_cms.mapping;

import com.example.hotel_cms.model.Hotel;
import com.example.hotel_cms.web.request.HotelRequest;
import com.example.hotel_cms.web.response.HotelResponse;
import com.example.hotel_cms.web.response.HotelResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(HotelRequest request);
    HotelResponse hotelToResponse(Hotel hotel);

    default HotelResponseList hotelListToHotelResponseList(List<Hotel> hotels){
        List<HotelResponse> hotelResponses =
                hotels.stream().map(this::hotelToResponse).collect(Collectors.toList());

        HotelResponseList response = new HotelResponseList();
        response.setHotels(hotelResponses);

        return response;
    }

}
