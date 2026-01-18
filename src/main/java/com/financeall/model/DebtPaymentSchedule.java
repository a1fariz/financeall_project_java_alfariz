package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "debt_payment_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtPaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private LocalDate dueDate;

    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "debt_item_id")
    private DebtItem debtItem;
}
