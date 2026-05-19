package com.financeall.service;

import com.financeall.dto.FICalculationResult;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FIService {

    public FICalculationResult performCalculation(BigDecimal monthlyExpense, BigDecimal withdrawalRate, BigDecimal currentAssets) {
       
         if (monthlyExpense == null || monthlyExpense.compareTo(BigDecimal.ZERO) <= 0)
        throw new IllegalArgumentException("Pengeluaran bulanan harus lebih dari 0");
    if (withdrawalRate == null || withdrawalRate.compareTo(BigDecimal.ZERO) <= 0
            || withdrawalRate.compareTo(new BigDecimal("100")) > 0)
        throw new IllegalArgumentException("Withdrawal rate harus antara 0.1% - 100%");

        // Rumus FI: Pengeluaran Tahunan / (Withdrawal Rate / 100)
        BigDecimal annualExpense = monthlyExpense.multiply(new BigDecimal("12"));

        BigDecimal rateDecimal = withdrawalRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal fiNumber = annualExpense.divide(rateDecimal, 2, RoundingMode.HALF_UP);
        
        // Hitung Gap (Kekurangan)
        BigDecimal gap = fiNumber.subtract(currentAssets).max(BigDecimal.ZERO);
        
        // Hitung Progres dalam Persen
        double progress = 0;
        if (fiNumber.compareTo(BigDecimal.ZERO) > 0) {
            progress = currentAssets.divide(fiNumber, 4, RoundingMode.HALF_UP)
                                   .multiply(new BigDecimal("100")).doubleValue();
        }

        return FICalculationResult.builder()
                .fiNumber(fiNumber)
                .currentInvestments(currentAssets)
                .gap(gap)
                .progressPercentage(Math.min(progress, 100.0)) // Maksimal 100%
                .build();
    }
}