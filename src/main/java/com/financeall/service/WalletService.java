package com.financeall.service;

import com.financeall.model.*;
import com.financeall.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRecordRepository transactionRepository;

// Tambahkan proteksi null pada sumTotal
public Map<String, Object> getMonthlyStats(User user) {
    LocalDateTime start = YearMonth.now().atDay(1).atStartOfDay();
    LocalDateTime end = YearMonth.now().atEndOfMonth().atTime(LocalTime.MAX);
    
    BigDecimal income = transactionRepository.sumTotalByTypeAndUserAndDateRange(user, "INCOME", start, end);
    BigDecimal expense = transactionRepository.sumTotalByTypeAndUserAndDateRange(user, "EXPENSE", start, end);
    
    income = (income != null) ? income : BigDecimal.ZERO;
    expense = (expense != null) ? expense : BigDecimal.ZERO;

    Map<String, Object> stats = new HashMap<>();
    stats.put("income", income);
    stats.put("expense", expense);
    stats.put("cashflow", income.subtract(expense));
    return stats;
}

    public BigDecimal getGlobalSystemBalance() {
        BigDecimal total = walletRepository.sumAllBalances();
        return (total != null) ? total : BigDecimal.ZERO;
    }

    @Transactional
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}