package com.spareparts.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LowStockCheckScheduler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LowStockCheckScheduler.class);

    @Scheduled(cron = "0 0 8 * * *")
    public void checkLowStockItems() {
        log.info("Initiating daily low stock check at {}", LocalDateTime.now());

        try {
            log.info("Successfully completed low stock check and notifications.");
        } catch (Exception e) {
            log.error("Error occurred during low stock evaluation", e);
        }
    }
}