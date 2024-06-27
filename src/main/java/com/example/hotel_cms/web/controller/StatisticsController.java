package com.example.hotel_cms.web.controller;

import com.example.hotel_cms.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadCsv() {
        String csvFile = statisticsService.exportStatisticsToCsv();
        FileSystemResource resource = new FileSystemResource(csvFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFile)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }
}