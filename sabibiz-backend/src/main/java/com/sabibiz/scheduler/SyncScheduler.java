package com.sabibiz.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

    @Scheduled(cron = "0 0 */6 * * *")
    public void runSync() {
        // TODO: implement synchronization tasks
    }
}
