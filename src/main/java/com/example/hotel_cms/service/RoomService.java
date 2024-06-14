package com.example.hotel_cms.service;

import com.example.hotel_cms.model.Room;
import com.example.hotel_cms.web.response.RoomResponse;

import java.util.List;

public interface RoomService {

    Room create(Room room, Long hotelId);
    Room update(Long id, Room room, Long hotelId);
    Room findById(Long id);
    List<Room> findAll();
    void deleteById(Long id);
}
