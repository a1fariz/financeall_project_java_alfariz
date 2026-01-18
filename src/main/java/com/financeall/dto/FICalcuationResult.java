package com.financeall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FICalcuationResult {
    private BigDecimal fiNumber;           // Target angka kebebasan
    private BigDecimal currentInvestments; // Aset saat ini
    private BigDecimal gap;                // Kekurangan (Target - Aset)
    private double progressPercentage;     // Persentase capaian (0-100%)
}