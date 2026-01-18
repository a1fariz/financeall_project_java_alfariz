package com.financeall.service;

import com.financeall.model.*;
import com.financeall.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelService {
    private final LevelRepository levelRepository;
    private final UserLevelProgressRepository progressRepository;

    public List<Level> findAll() { return levelRepository.findAll(); }

    @Transactional
    public UserLevelProgress getUserLevelData(User user) {
        return progressRepository.findByUser(user).orElseGet(() -> initializeUserProgress(user));
    }

    // Metode bantuan agar Controller tidak memproses logika null
    public Level getUserLevel(User user) {
        UserLevelProgress progress = getUserLevelData(user);
        return progress != null ? progress.getLevel() : null;
    }

    private UserLevelProgress initializeUserProgress(User user) {
        Level firstLevel = levelRepository.findByName("Newbie").orElse(null);
        return progressRepository.save(UserLevelProgress.builder()
                .user(user)
                .currentPoints(0)
                .level(firstLevel)
                .build());
    }

    @Transactional
    public void addPoints(User user, int points) {
        UserLevelProgress progress = getUserLevelData(user);
        progress.setCurrentPoints(progress.getCurrentPoints() + points);
        checkLevelUp(progress);
        progressRepository.save(progress);
    }

    private void checkLevelUp(UserLevelProgress progress) {
        levelRepository.findAll().forEach(level -> {
            if (progress.getCurrentPoints() >= level.getRequiredPoints()) {
                progress.setLevel(level);
            }
        });
    }
}