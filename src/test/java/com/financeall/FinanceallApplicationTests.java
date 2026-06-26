package com.financeall;

import com.financeall.dto.FICalculationResult;
import com.financeall.service.FIService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for FinanceAll business logic.
 *
 * A full {@code @SpringBootTest contextLoads()} is intentionally avoided here:
 * the app runs with spring.jpa.hibernate.ddl-auto=validate + Flyway against
 * PostgreSQL, so loading the full context requires a live database. These pure
 * unit tests exercise the FI calculation rules and run in any environment.
 */
class FinanceallApplicationTests {

    private final FIService fiService = new FIService();

    @Test
    void fiNumber_isAnnualExpenseDividedByWithdrawalRate() {
        // 10jt/bulan -> 120jt/tahun ; 4% rate -> 120jt / 0.04 = 3.000.000.000
        FICalculationResult result = fiService.performCalculation(
                new BigDecimal("10000000"),
                new BigDecimal("4"),
                BigDecimal.ZERO);

        assertEquals(0, new BigDecimal("3000000000.00").compareTo(result.getFiNumber()));
        assertEquals(0, new BigDecimal("3000000000.00").compareTo(result.getGap()));
        assertEquals(0.0, result.getProgressPercentage());
    }

    @Test
    void progress_isCappedAt100_andGapNeverNegative() {
        // Assets exceed the FI number: progress clamps to 100%, gap clamps to 0.
        FICalculationResult result = fiService.performCalculation(
                new BigDecimal("10000000"),
                new BigDecimal("4"),
                new BigDecimal("5000000000"));

        assertEquals(100.0, result.getProgressPercentage());
        assertEquals(0, BigDecimal.ZERO.compareTo(result.getGap()));
    }

    @Test
    void rejectsNonPositiveMonthlyExpense() {
        assertThrows(IllegalArgumentException.class, () -> fiService.performCalculation(
                BigDecimal.ZERO, new BigDecimal("4"), BigDecimal.ZERO));
    }

    @Test
    void rejectsOutOfRangeWithdrawalRate() {
        assertThrows(IllegalArgumentException.class, () -> fiService.performCalculation(
                new BigDecimal("10000000"), new BigDecimal("150"), BigDecimal.ZERO));
    }
}
