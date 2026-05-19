package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "debt_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default // FIX
    private BigDecimal amount = BigDecimal.ZERO;

    private String name;
    private String creditor;

    @Column(nullable = false) 
    @Builder.Default // FIX
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal interestRate = BigDecimal.ZERO;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BigDecimal getRemainingAmount() {
        BigDecimal total = (totalAmount != null) ? totalAmount : BigDecimal.ZERO;
        BigDecimal paid = (paidAmount != null) ? paidAmount : BigDecimal.ZERO;
        return total.subtract(paid);
    }

    public boolean isPaidOff() {
        return getRemainingAmount().compareTo(BigDecimal.ZERO) <= 0;
    }
}