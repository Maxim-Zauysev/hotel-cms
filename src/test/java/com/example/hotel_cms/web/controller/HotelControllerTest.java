package com.example.hotel_cms.web.controller;

import com.example.hotel_cms.AbstractTestController;
import com.example.hotel_cms.StringTestUtils;
import com.example.hotel_cms.exception.EntityNotFoundException;
import com.example.hotel_cms.mapping.HotelMapper;
import com.example.hotel_cms.model.Hotel;
import com.example.hotel_cms.service.HotelService;
import com.example.hotel_cms.service.ManageHotelService;
import com.example.hotel_cms.web.filter.HotelFilter;
import com.example.hotel_cms.web.request.HotelRequest;
import com.example.hotel_cms.web.response.HotelResponse;
import com.example.hotel_cms.web.response.HotelResponseList;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelControllerTest extends AbstractTestController {

    @MockBean
    private HotelService hotelService;
    @MockBean
    private HotelMapper hotelMapper;
    @MockBean
    private ManageHotelService manageHotelService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindAll_thenReturnAllHotels() throws Exception {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(createHotel(1L));
        hotels.add(createHotel(2L));

        List<HotelResponse> hotelResponses = new ArrayList<>();
        hotelResponses.add(createHotelResponse(1L));
        hotelResponses.add(createHotelResponse(2L));

        HotelResponseList hotelResponseList = new HotelResponseList(hotelResponses);

        HotelFilter hotelFilter = new HotelFilter();
        hotelFilter.setPageSize(10);
        hotelFilter.setPageNumber(0);

        Mockito.when(hotelService.findAll(hotelFilter)).thenReturn(hotels);
        Mockito.when(hotelMapper.hotelListToHotelResponseList(hotels)).thenReturn(hotelResponseList);

        String actualResponse = mockMvc.perform(get("/api/v1/hotel")
                        .param("pageSize", "10")
                        .param("pageNumber", "0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_hotels_response.json");

        Mockito.verify(hotelService, Mockito.times(1)).findAll(hotelFilter);
        Mockito.verify(hotelMapper, Mockito.times(1)).hotelListToHotelResponseList(hotels);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenFindById_thenReturnHotel() throws Exception {
        Hotel hotel = createHotel(1L);
        HotelResponse hotelResponse = createHotelResponse(1L);

        Mockito.when(hotelService.findById(1L)).thenReturn(hotel);
        Mockito.when(hotelMapper.hotelToResponse(hotel)).thenReturn(hotelResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/hotel/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_hotel_by_id_response.json");

        Mockito.verify(hotelService, Mockito.times(1)).findById(1L);
        Mockito.verify(hotelMapper, Mockito.times(1)).hotelToResponse(hotel);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenCreateHotel_thenReturnNewHotel() throws Exception{
        Hotel hotel = new Hotel();
        hotel.setName("hotel\s1");
        hotel.setHeadline("hotel");
        hotel.setCity("city");
        hotel.setAddress("address");
        hotel.setCenterDistance(0.0);
        hotel.setRating(0.0);
        hotel.setReviewCount(0);

        Hotel createdHotel = createHotel(1l);
        HotelResponse hotelResponse =createHotelResponse(1l);
        HotelRequest hotelRequest =  new HotelRequest();

        hotelRequest.setName("hotel\s1");
        hotelRequest.setHeadline("hotel");
        hotelRequest.setCity("city");
        hotelRequest.setAddress("address");
        hotelRequest.setCenterDistance(1.0);

        Mockito.when(hotelService.create(hotel)).thenReturn(createdHotel);
        Mockito.when(hotelMapper.requestToHotel(hotelRequest)).thenReturn(hotel);
        Mockito.when(hotelMapper.hotelToResponse(createdHotel)).thenReturn(hotelResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/hotel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hotelRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_hotel_response.json");

        Mockito.verify(hotelService,Mockito.times(1)).create(hotel);
        Mockito.verify(hotelMapper,Mockito.times(1)).requestToHotel(hotelRequest);
        Mockito.verify(hotelMapper,Mockito.times(1)).hotelToResponse(createdHotel);


        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenUpdateHotelThenReturnUpdatedHotel() throws Exception {
        HotelRequest request = new HotelRequest();

        request.setName("new\shotel\s1");
        request.setHeadline("hotel");
        request.setCity("city");
        request.setAddress("address");
        request.setCenterDistance(1.0);

        Hotel updatedHotel  = new Hotel();
        updatedHotel.setId(1l);
        updatedHotel.setName("new\shotel\s1");
        updatedHotel.setHeadline("hotel");
        updatedHotel.setCity("city");
        updatedHotel.setAddress("address");
        updatedHotel.setCenterDistance(0.0);
        updatedHotel.setRating(0.0);
        updatedHotel.setReviewCount(0);

        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.setId(1l);
        hotelResponse.setName("new\shotel\s1");
        hotelResponse.setHeadline("hotel");
        hotelResponse.setCity("city");
        hotelResponse.setAddress("address");
        hotelResponse.setCenterDistance(0.0);
        hotelResponse.setRating(0.0);
        hotelResponse.setReviewCount(0);

        Mockito.when(hotelService.update(1l,updatedHotel)).thenReturn(updatedHotel);
        Mockito.when(hotelMapper.requestToHotel(request)).thenReturn(updatedHotel);
        Mockito.when(hotelMapper.hotelToResponse(updatedHotel)).thenReturn(hotelResponse);


        String actualResponse = mockMvc.perform(put("/api/v1/hotel/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Mockito.verify(hotelService,Mockito.times(1)).update(1l,updatedHotel);
        Mockito.verify(hotelMapper,Mockito.times(1)).requestToHotel(request);
        Mockito.verify(hotelMapper,Mockito.times(1)).hotelToResponse(updatedHotel);

        String expectedResponse = StringTestUtils.readStringFromResource("response/update_hotel_response.json");
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenDeleteHotelById_ThenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/hotel/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(hotelService,Mockito.times(1)).deleteById(1l);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenGetNotExistedClient_ThenReturnError()throws Exception{
        Mockito.when(hotelService.findById(500l))
                .thenThrow(new EntityNotFoundException("hotel with id 500 not found"));

        var response = mockMvc.perform(get("/api/v1/hotel/500"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_by_id_not_found_response.json");

        Mockito.verify(hotelService,Mockito.times(1)).findById(500l);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);


    }
}
