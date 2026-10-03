package com.spareparts.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SalesReportScheduler {

    @Scheduled(cron = "0 0 0 * * *")
    public void generateDailySalesReport() {
        log.info("Starting daily sales report generation at {}", LocalDateTime.now());

        try {
            log.info("Successfully completed daily sales report generation.");
        } catch (Exception e) {
            log.error("Failed to generate daily sales report", e);
        }
    }
}