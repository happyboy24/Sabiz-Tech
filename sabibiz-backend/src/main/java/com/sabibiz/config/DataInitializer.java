package com.sabibiz.config;

import com.sabibiz.entity.Business;
import com.sabibiz.entity.Role;
import com.sabibiz.entity.User;
import com.sabibiz.repository.BusinessRepository;
import com.sabibiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("admin")) {
            log.info("Seeding default business and admin user...");
            Business business = new Business();
            business.setName("Demo Business");
            business.setBusinessNumber("BIZ-001");
            business.setEmail("admin@sabibiz.com");
            business.setIsActive(true);
            business = businessRepository.save(business);

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@sabibiz.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setRole(Role.ROLE_BUSINESS_OWNER);
            admin.setIsActive(true);
            admin.setBusiness(business);

            userRepository.save(admin);
            log.info("Default admin user created: admin / admin123");
        }
    }
}
