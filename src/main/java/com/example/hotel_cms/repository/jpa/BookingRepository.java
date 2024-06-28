package com.example.hotel_cms.repository.jpa;

import com.example.hotel_cms.model.Booking;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByRoomId(Long roomId);
    @EntityGraph(attributePaths = {"room"})
    List<Booking> findAll();
}
