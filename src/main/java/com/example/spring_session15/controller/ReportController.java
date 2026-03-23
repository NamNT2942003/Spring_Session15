package com.example.spring_session15.controller;

import com.example.spring_session15.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenue(@RequestParam(defaultValue = "month") String type) {
        Map<String, Object> response = new HashMap<>();
        response.put("type", type);
        response.put("revenue", reportService.getRevenue(type));
        return ResponseEntity.ok(response);
    }
}