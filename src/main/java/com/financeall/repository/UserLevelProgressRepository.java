package com.financeall.repository;

import com.financeall.model.User;
import com.financeall.model.UserLevelProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserLevelProgressRepository extends JpaRepository<UserLevelProgress, Long> {
    // Metode ini WAJIB ADA agar LevelService tidak error
    Optional<UserLevelProgress> findByUser(User user);
}