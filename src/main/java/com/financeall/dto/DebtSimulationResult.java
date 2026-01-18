package com.financeall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebtSimulationResult {
    private BigDecimal totalDebtInitial;
    private int monthsToPayOff;
    private LocalDate estimatedPayoffDate;
    private BigDecimal monthlyPaymentNeeded;
}