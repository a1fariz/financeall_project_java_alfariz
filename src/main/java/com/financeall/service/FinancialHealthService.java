// Lokasi: src/main/java/com/financeall/service/FinancialHealthService.java
package com.financeall.service;

import com.financeall.model.DebtItem;
import com.financeall.repository.DebtItemRepository;
import com.financeall.repository.TransactionRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FinancialHealthService {
    private final TransactionRecordRepository transactionRepository;
    private final DebtItemRepository debtRepository;

    public Map<String, Object> getGlobalSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        List<DebtItem> allDebts = debtRepository.findAll();
        
        BigDecimal totalDebt = allDebts.stream()
    .filter(Objects::nonNull)
    .map(d -> {
        BigDecimal remaining = d.getRemainingAmount();
        return remaining != null ? remaining : BigDecimal.ZERO;
    })
    .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        stats.put("totalSystemDebt", totalDebt);
        stats.put("totalTransactions", transactionRepository.count());
        return stats;
    }
}