package com.sabibiz.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LowStockScheduler {

    @Scheduled(cron = "0 0 * * * *")
    public void checkLowStock() {
        // TODO: implement low stock checks and notifications
    }
}
