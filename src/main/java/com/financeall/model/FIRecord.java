package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fi_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FIRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // =========================
    // INPUT DATA
    // =========================

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal monthlyExpense = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal currentInvestment = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal monthlyInvestment = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal annualReturn = BigDecimal.ZERO;

    // =========================
    // RESULT
    // =========================

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal fiTarget = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private Integer estimatedYears = 0;

    // =========================
    // TIMESTAMP
    // =========================

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}