package com.spareparts.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SalesReportScheduler {

    private static final Logger log = LoggerFactory.getLogger(SalesReportScheduler.class);

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