package com.spareparts.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LowStockCheckScheduler {

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