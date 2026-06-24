package com.financeall.service;

import com.financeall.model.DebtItem;
import com.financeall.model.User;
import com.financeall.model.Wallet;
import com.financeall.repository.DebtItemRepository;
import com.financeall.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DebtService {
    private final DebtItemRepository repository;
    private final WalletRepository walletRepository; // FIX: Inject Wallet Repo

    public List<DebtItem> getSortedDebts(Long userId, String strategy) {
        List<DebtItem> debts = repository.findByUserId(userId);
        if ("avalanche".equalsIgnoreCase(strategy)) { // FIX: Pake ignoreCase
            debts.sort(Comparator.comparing(DebtItem::getInterestRate, Comparator.nullsLast(Comparator.reverseOrder())));
        } else {
            debts.sort(Comparator.comparing(DebtItem::getRemainingAmount));
        }
        return debts;
    }

    public void createDebt(User user, String creditor, BigDecimal amount, String dueDateStr) {
        LocalDate date = (dueDateStr != null && !dueDateStr.isEmpty()) ? LocalDate.parse(dueDateStr) : null;
        DebtItem debt = DebtItem.builder()
                .creditor(creditor)
                .totalAmount(amount)
                .paidAmount(BigDecimal.ZERO)
                .interestRate(BigDecimal.ZERO)
                .dueDate(date)
                .user(user)
                .build();
        repository.save(debt);
    }

    @Transactional
    public void payDebt(Long debtId, BigDecimal amount, Long authenticatedUserId) {
        DebtItem debt = repository.findById(debtId)
                .orElseThrow(() -> new RuntimeException("Data hutang tidak ditemukan"));

        // SECURITY FIX (IDOR): Validate that this debt belongs to the authenticated user.
        // Previously, any user could submit another user's debtId to drain their wallet.
        if (!debt.getUser().getId().equals(authenticatedUserId)) {
            throw new RuntimeException("Akses ditolak: Ini bukan hutang Anda!");
        }

        Wallet wallet = walletRepository.findByUser(debt.getUser())
                .orElseThrow(() -> new RuntimeException("Dompet tidak ditemukan!"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Nominal pembayaran harus lebih dari 0!");
        }

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo dompet tidak cukup untuk bayar utang!");
        }

        BigDecimal currentPaid = debt.getPaidAmount() != null ? debt.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal remaining = debt.getTotalAmount().subtract(currentPaid);
        BigDecimal actualPayment = amount.compareTo(remaining) > 0 ? remaining : amount;

        wallet.setBalance(wallet.getBalance().subtract(actualPayment));
        debt.setPaidAmount(currentPaid.add(actualPayment));

        walletRepository.save(wallet);
        repository.save(debt);
    }

    public BigDecimal calculateTotalDebt(User user) {
        return repository.findByUserId(user.getId()).stream()
                .map(item -> item.getRemainingAmount() != null ? item.getRemainingAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void deleteByIdAndUser(Long debtId, Long userId) {
    DebtItem debt = repository.findById(debtId)
        .filter(d -> d.getUser().getId().equals(userId))
        .orElseThrow(() -> new RuntimeException("Hutang tidak ditemukan atau bukan milik Anda"));
    repository.delete(debt);
}
    
    // NOTE: Removed unsecured public delete(Long id). Use deleteByIdAndUser() instead.
}