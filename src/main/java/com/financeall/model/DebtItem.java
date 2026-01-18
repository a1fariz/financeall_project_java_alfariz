package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String creditor;
    
    @Column(columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal totalAmount;

    @Column(columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal paidAmount;

    @Column(columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal interestRate;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BigDecimal getRemainingAmount() {
        BigDecimal total = (totalAmount != null) ? totalAmount : BigDecimal.ZERO;
        BigDecimal paid = (paidAmount != null) ? paidAmount : BigDecimal.ZERO;
        return total.subtract(paid);
    }
}