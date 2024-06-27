package com.example.hotel_cms.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "userEvents")
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    @Id
    private String id;
    private Long userId;
}
