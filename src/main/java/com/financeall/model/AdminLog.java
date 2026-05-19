package com.financeall.model;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_logs")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLog
        implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // ADMIN USER
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(
            name = "user_id",
            nullable = false
    )

    private User user;

    // =========================
    // ACTION
    // =========================

    @Column(nullable = false)
    private String action;

    // =========================
    // DETAIL
    // =========================

    @Column(columnDefinition = "TEXT")
    private String detail;

    // =========================
    // IP ADDRESS
    // =========================

    private String ipAddress;

    // =========================
    // TIMESTAMP
    // =========================

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // =========================
    // AUTO TIMESTAMP
    // =========================

    @PrePersist
    protected void onCreate() {

        createdAt =
                LocalDateTime.now();
    }
}