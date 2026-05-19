package com.financeall.service;

import com.financeall.model.TransactionType;
import com.financeall.model.User;
import com.financeall.model.Wallet;
import com.financeall.repository.TransactionRecordRepository;
import com.financeall.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final TransactionRecordRepository
            transactionRepository;

    // =====================================================
    // MONTHLY STATS
    // =====================================================

    public Map<String, Object> getMonthlyStats(
            User user
    ) {

        LocalDate start =
                YearMonth.now()
                        .atDay(1);

        LocalDate end =
                YearMonth.now()
                        .atEndOfMonth();

        BigDecimal income =
                transactionRepository
                        .sumTotalByTypeAndUserAndDateRange(
                                user,
                                TransactionType.INCOME,
                                start,
                                end
                        );

        BigDecimal expense =
                transactionRepository
                        .sumTotalByTypeAndUserAndDateRange(
                                user,
                                TransactionType.EXPENSE,
                                start,
                                end
                        );

        // NULL SAFETY
        income = (income != null)
                ? income
                : BigDecimal.ZERO;

        expense = (expense != null)
                ? expense
                : BigDecimal.ZERO;

        Map<String, Object> stats =
                new HashMap<>();

        stats.put("income", income);

        stats.put("expense", expense);

        stats.put(
                "cashflow",
                income.subtract(expense)
        );

        return stats;
    }

    // =====================================================
    // GLOBAL SYSTEM BALANCE
    // =====================================================

    public BigDecimal getGlobalSystemBalance() {

        BigDecimal total =
                walletRepository.sumAllBalances();

        return (total != null)
                ? total
                : BigDecimal.ZERO;
    }

    // =====================================================
    // SAVE WALLET
    // =====================================================

    @Transactional
    public Wallet save(Wallet wallet) {

        return walletRepository.save(wallet);
    }
}