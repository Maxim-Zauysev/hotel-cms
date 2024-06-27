package com.example.hotel_cms.repository;

import com.example.hotel_cms.model.kafka.BookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEventRepository extends MongoRepository<BookingEvent,String> {
}
