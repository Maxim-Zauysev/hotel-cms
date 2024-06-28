package com.example.hotel_cms.repository.jpa;

import com.example.hotel_cms.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> , JpaSpecificationExecutor<Hotel> {
    Page<Hotel> findAll(Pageable page);
}
