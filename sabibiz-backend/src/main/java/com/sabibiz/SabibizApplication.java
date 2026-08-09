package com.sabibiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@org.springframework.data.jpa.repository.config.EnableJpaAuditing
public class SabibizApplication {
    public static void main(String[] args) {
        SpringApplication.run(SabibizApplication.class, args);
    }
}
