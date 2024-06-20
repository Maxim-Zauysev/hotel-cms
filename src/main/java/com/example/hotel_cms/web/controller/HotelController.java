package com.example.hotel_cms.web.controller;

import com.example.hotel_cms.mapping.HotelMapper;
import com.example.hotel_cms.model.Hotel;
import com.example.hotel_cms.service.HotelService;
import com.example.hotel_cms.web.filter.HotelFilter;
import com.example.hotel_cms.web.request.HotelRequest;
import com.example.hotel_cms.web.response.HotelResponse;
import com.example.hotel_cms.web.response.HotelResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> create(@Valid @RequestBody HotelRequest request){
        Hotel newHotel = hotelService.create(hotelMapper.requestToHotel(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(newHotel));
    }

    @GetMapping
    public ResponseEntity<HotelResponseList> findAll(HotelFilter filter){
        return ResponseEntity.ok(hotelMapper.hotelListToHotelResponseList(
                hotelService.findAll(filter)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(hotelMapper.hotelToResponse(
                hotelService.findById(id)
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @Valid @RequestBody HotelRequest request){
        Hotel updatedHotel = hotelService.update(id,hotelMapper.requestToHotel(request));
        return ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
