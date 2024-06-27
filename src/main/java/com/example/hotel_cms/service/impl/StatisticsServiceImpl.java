package com.example.hotel_cms.service.impl;

import com.example.hotel_cms.model.kafka.BookingEvent;
import com.example.hotel_cms.model.kafka.UserEvent;
import com.example.hotel_cms.repository.BookingEventRepository;
import com.example.hotel_cms.repository.UserEventRepository;
import com.example.hotel_cms.service.StatisticsService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final MongoTemplate mongoTemplate;
    private final UserEventRepository userEventRepository;
    private final BookingEventRepository bookingEventRepository;

    @Override
    public String exportStatisticsToCsv() {
        String csvFile = "statistics.csv";
        List<BookingEvent> bookingEvents = bookingEventRepository.findAll();
        List<UserEvent> userEvents = userEventRepository.findAll();

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            writer.writeNext(new String[]{"Event Type", "User ID", "Check-in Date", "Check-out Date"});

            for (BookingEvent event : bookingEvents) {
                writer.writeNext(new String[]{
                        "Booking",
                        event.getUserId().toString(),
                        event.getCheckInDate() != null ? event.getCheckInDate().toString() : "",
                        event.getCheckOutDate() != null ? event.getCheckOutDate().toString() : ""
                });
            }

            for (UserEvent event : userEvents) {
                writer.writeNext(new String[]{
                        "User Registration",
                        event.getUserId().toString()
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return csvFile;
    }
}