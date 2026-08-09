package com.sabibiz.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DebtReminderScheduler {

    @Scheduled(cron = "0 30 9 * * *")
    public void sendDebtReminders() {
        // TODO: implement debt reminder notifications
    }
}
