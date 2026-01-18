package com.financeall.service;

import com.financeall.dto.FICalcuationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class FIService {

    public FICalcuationResult performCalculation(BigDecimal monthlyExpense, BigDecimal withdrawalRate) {
        // Logika: FI Number = (Pengeluaran Tahunan) / (Safe Withdrawal Rate)
        // Rule of 25 jika rate 4%
        BigDecimal annualExpense = monthlyExpense.multiply(new BigDecimal("12"));
        BigDecimal rateDecimal = withdrawalRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        
        BigDecimal fiNumber = annualExpense.divide(rateDecimal, 2, RoundingMode.HALF_UP);

        return FICalcuationResult.builder()
                .fiNumber(fiNumber)
                .gap(fiNumber) // Gap dihitung dari selisih aset saat ini
                .build();
    }
}