package com.example.spring_session15.service;

import com.example.spring_session15.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;

    public BigDecimal getRevenue(String type) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        LocalDate today = LocalDate.now();

        switch (type.toLowerCase()) {
            case "day":
                startDate = today.atStartOfDay();
                endDate = today.atTime(LocalTime.MAX);
                break;
            case "month":
                startDate = today.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                endDate = today.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
                break;
            case "year":
                startDate = today.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
                endDate = today.with(TemporalAdjusters.lastDayOfYear()).atTime(LocalTime.MAX);
                break;
            default:
                throw new IllegalArgumentException("Type không hợp lệ. Vui lòng chọn day, month, hoặc year");
        }

        return orderRepository.calculateRevenue(startDate, endDate);
    }
}