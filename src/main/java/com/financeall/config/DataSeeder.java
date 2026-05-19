package com.financeall.config;

import com.financeall.model.Level;
import com.financeall.model.User;
import com.financeall.repository.LevelRepository;
import com.financeall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepo;
    private final LevelRepository levelRepo;
    private final PasswordEncoder passwordEncoder;
    

    @Override
    public void run(String... args) {
        // Seed Levels
        if (levelRepo.count() == 0) {
            levelRepo.save(Level.builder().name("Newbie").requiredPoints(0).build());
            levelRepo.save(Level.builder().name("Saver").requiredPoints(100).build());
            levelRepo.save(Level.builder().name("Investor").requiredPoints(500).build());
            System.out.println("✅ Levels Seeded");
        }

        // Seed Admin
        if (userRepo.findByUsername("admin") == null) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@financeall.com")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Super Admin")
                    .role("ADMIN")
                    .recoveryPin("123456")
                    .build();
            userRepo.save(admin);
            System.out.println("✅ Admin Seeded");
        }
    }
}