package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "transaction_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "INCOME" atau "EXPENSE"

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    private BigDecimal amount;

    // Ingat: Nama field ini description, bukan note
    private String description;

@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}