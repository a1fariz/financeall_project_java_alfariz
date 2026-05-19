package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // USER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // INFO
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    // MONEY
    private BigDecimal amount;

    // DATE
    private LocalDate transactionDate;
}