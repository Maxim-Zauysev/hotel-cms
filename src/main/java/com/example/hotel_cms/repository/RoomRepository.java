package com.example.hotel_cms.repository;

import com.example.hotel_cms.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @EntityGraph(attributePaths = {"hotel","unavailableDates"})
    List<Room> findAll();
}
