package com.example.hotel_cms.listener;

import com.example.hotel_cms.model.kafka.UserEvent;
import com.example.hotel_cms.repository.mongo.UserEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventListener {

    @Value("${app.kafka.kafkaUserTopic}")
    private String topicName;

    private final UserEventRepository userEventRepository;

    @KafkaListener(topics = "${app.kafka.kafkaUserTopic}",
            groupId = "${app.kafka.kafkaGroupId}",
            containerFactory = "userEventConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload UserEvent message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY,required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp){

        userEventRepository.save(message);
        log.info("Received message: {}", message);

    }
}