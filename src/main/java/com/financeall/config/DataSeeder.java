package com.financeall.config;

import com.financeall.model.Level;
import com.financeall.model.User;
import com.financeall.repository.LevelRepository;
import com.financeall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepo;
    private final LevelRepository levelRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.email:admin@financeall.com}")
    private String adminEmail;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Value("${app.admin.recovery-pin:123456}")
    private String adminRecoveryPin;


    @Override
    public void run(String... args) {
        // Seed Levels
        if (levelRepo.count() == 0) {
            levelRepo.save(Level.builder().name("Newbie").requiredPoints(0).build());
            levelRepo.save(Level.builder().name("Saver").requiredPoints(100).build());
            levelRepo.save(Level.builder().name("Investor").requiredPoints(500).build());
            System.out.println("Levels Seeded");
        }

        // Seed Admin
        if (userRepo.findByUsername(adminUsername) == null) {
            User admin = User.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .fullName("Super Admin")
                    .role("ADMIN")
                    // FIX: recovery PIN must be hashed — resetPassword() uses passwordEncoder.matches()
                    .recoveryPin(passwordEncoder.encode(adminRecoveryPin))
                    .build();
            userRepo.save(admin);
            System.out.println("Admin Seeded");
        }
    }
}