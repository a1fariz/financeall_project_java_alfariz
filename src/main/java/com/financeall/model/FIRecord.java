package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fi_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FIRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal passiveIncome;

    private BigDecimal monthlyExpense;

    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
