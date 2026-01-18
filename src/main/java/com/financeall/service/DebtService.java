package com.financeall.service;

import com.financeall.model.DebtItem;
import com.financeall.model.User;
import com.financeall.repository.DebtItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DebtService {
    private final DebtItemRepository repository;

    public List<DebtItem> getSortedDebts(Long userId, String strategy) {
        List<DebtItem> debts = repository.findByUserId(userId);
        if ("avalanche".equals(strategy)) {
            debts.sort(Comparator.comparing(DebtItem::getInterestRate, Comparator.nullsLast(Comparator.reverseOrder())));
        } else {
            // Default Snowball: urutkan dari sisa hutang terkecil
            debts.sort(Comparator.comparing(DebtItem::getRemainingAmount));
        }
        return debts;
    }

    public void createDebt(User user, String creditor, BigDecimal amount, String dueDateStr) {
        LocalDate date = (dueDateStr != null && !dueDateStr.isEmpty()) ? LocalDate.parse(dueDateStr) : null;
        DebtItem debt = DebtItem.builder()
                .creditor(creditor)
                .totalAmount(amount)
                .paidAmount(BigDecimal.ZERO) // Awalnya 0
                .interestRate(BigDecimal.ZERO)
                .dueDate(date)
                .user(user)
                .build();
        repository.save(debt);
    }

    public void payDebt(Long debtId, BigDecimal amount) {
        DebtItem debt = repository.findById(debtId)
                .orElseThrow(() -> new RuntimeException("Data hutang tidak ditemukan"));
        
        BigDecimal currentPaid = debt.getPaidAmount() != null ? debt.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal newPaid = currentPaid.add(amount);
        
        // Pastikan tidak membayar lebih dari total hutang
        if (newPaid.compareTo(debt.getTotalAmount()) > 0) {
            newPaid = debt.getTotalAmount();
        }
        
        debt.setPaidAmount(newPaid);
        repository.save(debt);
    }

    public BigDecimal calculateTotalDebt(User user) {
        return repository.findByUserId(user.getId()).stream()
                .map(item -> item.getRemainingAmount() != null ? item.getRemainingAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void delete(Long id) { repository.deleteById(id); }
}