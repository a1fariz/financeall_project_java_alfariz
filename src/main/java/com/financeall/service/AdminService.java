package com.financeall.service;

import com.financeall.model.*;
import com.financeall.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final WalletRepository walletRepository;

    private final AnnouncementRepository
            announcementRepository;

    private final DebtItemRepository
            debtRepository;

    private final AdminLogRepository
            adminLogRepository;

    private final LevelRepository
            levelRepository;

    private final TransactionRecordRepository
            transactionRepository;

    private final PasswordEncoder
            passwordEncoder;

    // =========================
    // SAVE ADMIN LOG
    // =========================

    public void saveLog(
            User admin,
            String action,
            String detail
    ) {

        if (admin == null) {
            return;
        }

        AdminLog log =
                AdminLog.builder()

                        .user(admin)

                        .action(action)

                        .detail(detail)

                        .build();

        adminLogRepository.save(log);
    }

    // =========================
    // ANNOUNCEMENT
    // =========================

    public List<Announcement> getAllArticles() {

        return announcementRepository
                .findAll()

                .stream()

                .sorted(
                        Comparator.comparing(
                                Announcement::getCreatedAt,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        )
                )

                .collect(Collectors.toList());
    }

    @Transactional
    public void saveArticle(
            String title,
            String content,
            User admin
    ) {

        Announcement article =
                Announcement.builder()

                        .title(title)

                        .content(content)

                        .createdAt(LocalDateTime.now())

                        .build();

        announcementRepository.save(article);

        saveLog(
                admin,
                "BROADCAST",
                "Mengirim broadcast: " + title
        );
    }

    @Transactional
    public void deleteArticle(
            Long id,
            User admin
    ) {

        Announcement article =
                announcementRepository
                        .findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Artikel tidak ditemukan"
                                )
                        );

        announcementRepository.delete(article);

        saveLog(
                admin,
                "DELETE_ARTICLE",
                "Menghapus artikel: "
                        + article.getTitle()
        );
    }

    // =========================
    // UPDATE PROFILE
    // =========================

    @Transactional
    public void updateAdminProfile(
            Long id,
            String username,
            String email,
            String password,
            User admin
    ) {

        if (id == null) {

            throw new IllegalArgumentException(
                    "ID user tidak boleh null"
            );
        }

        User u =
                userRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User tidak ditemukan"
                                )
                        );

        u.setUsername(username);

        u.setEmail(email);

        if (password != null
                && !password.isBlank()) {

            u.setPassword(
                    passwordEncoder.encode(password)
            );
        }

        userRepository.save(u);

        saveLog(
                admin,
                "UPDATE_PROFILE",
                "Update profile user: "
                        + u.getUsername()
        );
    }

    // =========================
    // GLOBAL ANALYTICS
    // =========================

    public Map<String, Object> getGlobalStats() {

        Map<String, Object> stats =
                new HashMap<>();

        BigDecimal income =
                transactionRepository
                        .sumAllIncome();

        BigDecimal expense =
                transactionRepository
                        .sumAllExpense();

        BigDecimal totalDebt =

                debtRepository.findAll()

                        .stream()

                        .map(i ->

                                (i != null
                                        && i.getTotalAmount() != null)

                                        ? i.getTotalAmount()

                                        : BigDecimal.ZERO
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal balance =

                walletRepository.findAll()

                        .stream()

                        .map(w ->

                                (w != null
                                        && w.getBalance() != null)

                                        ? w.getBalance()

                                        : BigDecimal.ZERO
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        stats.put(
                "totalIncome",
                income != null
                        ? income
                        : BigDecimal.ZERO
        );

        stats.put(
                "totalExpense",
                expense != null
                        ? expense
                        : BigDecimal.ZERO
        );

        stats.put(
                "totalSystemDebt",
                totalDebt
        );

        stats.put(
                "globalBalance",
                balance
        );

        return stats;
    }

    // =========================
    // USER COUNT
    // =========================

    public long countUsers() {

        return userRepository.count();
    }

    // =========================
    // LATEST ARTICLES
    // =========================

    public List<Announcement> getLatestArticles() {

        return announcementRepository
                .findAll()

                .stream()

                .sorted(
                        Comparator.comparing(
                                Announcement::getCreatedAt,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        )
                )

                .collect(Collectors.toList());
    }

    // =========================
    // USER MANAGEMENT
    // =========================

    public List<User> findAllUsers(
            String keyword,
            String sortBy
    ) {

        List<User> users =

                (keyword != null
                        && !keyword.isBlank())

                        ? userRepository
                        .findByUsernameContainingIgnoreCase(
                                keyword
                        )

                        : userRepository.findAll();

        Comparator<User> comparator =

                "email".equalsIgnoreCase(sortBy)

                        ? Comparator.comparing(
                        User::getEmail,
                        String.CASE_INSENSITIVE_ORDER
                )

                        : Comparator.comparing(
                        User::getUsername,
                        String.CASE_INSENSITIVE_ORDER
                );

        users.sort(comparator);

        return users;
    }

    @Transactional
    public void toggleUserStatus(
            Long id,
            User admin
    ) {

        User u =
                userRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User tidak ditemukan"
                                )
                        );

        u.setBanned(!u.isBanned());

        userRepository.save(u);

        saveLog(
                admin,
                "TOGGLE_BAN",
                "Status ban user "
                        + u.getUsername()
                        + " diubah"
        );
    }

    @Transactional
    public void deleteUser(
            Long id,
            User admin
    ) {

        User u =
                userRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User tidak ditemukan"
                                )
                        );

        String username =
                u.getUsername();

        // CascadeType.ALL + orphanRemoval pada User entity
        // akan otomatis menghapus semua relasi (wallet, transactions, debts, dll)
        userRepository.delete(u);

        saveLog(
                admin,
                "DELETE_USER",
                "Menghapus user: "
                        + username
        );
    }

    // =========================
    // LEVEL MANAGEMENT
    // =========================

    public List<Level> findAllLevels() {

        return levelRepository.findAll();
    }

    public List<AdminLog> getAllLogs() {

        return adminLogRepository
                .findTop10ByOrderByCreatedAtDesc();
    }

    @Transactional
    public void saveLevel(
            Long id,
            String name,
            Integer points,
            User admin
    ) {

        Level level;

        String action;

        int finalPoints =
                (points != null)
                        ? points
                        : 0;

        if (id != null && id > 0) {

            level =
                    levelRepository.findById(id)

                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Level tidak ditemukan"
                                    )
                            );

            level.setName(name);

            level.setRequiredPoints(finalPoints);

            action = "UPDATE_LEVEL";

        } else {

            level =
                    Level.builder()

                            .name(name)

                            .requiredPoints(finalPoints)

                            .build();

            action = "ADD_LEVEL";
        }

        levelRepository.save(level);

        saveLog(
                admin,
                action,
                "Nama Level: "
                        + name
                        + " ("
                        + finalPoints
                        + " XP)"
        );
    }

    @Transactional
    public void deleteLevel(
            Long id,
            User admin
    ) {

        Level level =
                levelRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Level tidak ditemukan"
                                )
                        );

        levelRepository.delete(level);

        saveLog(
                admin,
                "DELETE_LEVEL",
                "Menghapus level: "
                        + level.getName()
        );
    }
}