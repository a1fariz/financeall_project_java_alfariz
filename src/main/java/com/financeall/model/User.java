package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String role;
    private String recoveryPin;
    
    @Column(columnDefinition = "boolean default false")
    private boolean isBanned = false;

    public boolean isEnabled() {
    return !isBanned; // Agar th:text="${u.enabled}" tidak error
    }
    // --- RELASI ONE-TO-ONE ---
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserLevelProgress userLevelProgress;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private EmergencyFund emergencyFund;

    // --- RELASI ONE-TO-MANY (Tambahkan yang ini agar Service lain tidak merah) ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TransactionRecord> transactionRecords;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DebtItem> debtItems; // Wajib ada untuk DebtService

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FIRecord> fiRecords; // Wajib ada untuk FIService

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AdminLog> adminLogs;
}