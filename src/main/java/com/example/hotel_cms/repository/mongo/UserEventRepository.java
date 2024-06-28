package com.example.hotel_cms.repository.mongo;

import com.example.hotel_cms.model.kafka.UserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRepository extends MongoRepository<UserEvent,String> {
}
