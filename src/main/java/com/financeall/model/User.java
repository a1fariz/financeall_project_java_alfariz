package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
        private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // BASIC INFO
    // =========================

    @Column(nullable = false,
            unique = true,
            length = 50)
    private String username;

    @Column(nullable = false,
            unique = true,
            length = 100)
    private String email;

    @Column(nullable = false,
            length = 255)
    private String password;

    @Column(nullable = false,
            length = 100)
    private String fullName;

    @Column(nullable = false,
            length = 20)
    private String role;

    @Column(length = 255)
    private String recoveryPin;

    @Column(nullable = false)
    private boolean isBanned = false;

    // =========================
    // AUDIT TIMESTAMP
    // =========================

    @Column(nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // =========================
    // RELATIONSHIPS
    // =========================

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Wallet wallet;

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private UserLevelProgress userLevelProgress;

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private EmergencyFund emergencyFund;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<TransactionRecord> transactionRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<DebtItem> debtItems = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<FIRecord> fiRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<AdminLog> adminLogs = new ArrayList<>();

    // =========================
    // LIFECYCLE
    // =========================

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (role == null || role.isBlank()) {
            role = "USER";
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // =========================
    // HELPER METHOD
    // =========================

    public boolean isEnabled() {
        return !isBanned;
    }
}