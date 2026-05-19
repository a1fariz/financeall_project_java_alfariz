package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "emergency_funds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Builder.Default // FIX: Mencegah Null saat menggunakan Builder
    private BigDecimal targetAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX: Mencegah Null saat menggunakan Builder
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX: Mencegah Null saat menggunakan Builder
    private BigDecimal monthlySaving = BigDecimal.ZERO;
}