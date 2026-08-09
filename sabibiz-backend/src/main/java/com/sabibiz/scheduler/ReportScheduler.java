package com.sabibiz.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportScheduler {

    @Scheduled(cron = "0 0 1 * * *")
    public void generateReports() {
        // TODO: implement daily report generation
    }
}
