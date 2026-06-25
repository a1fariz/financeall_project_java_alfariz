package com.financeall.service;

import com.financeall.model.*;
import com.financeall.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    /** Points awarded for each recorded transaction (drives level progression). */
    private static final int POINTS_PER_TRANSACTION = 10;

    private final TransactionRecordRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AdminService adminService;
    private final LevelService levelService;

    public Page<TransactionRecord> findUserTransactions(User user, int page) {
        return transactionRepository.findByUser(
                user,
                PageRequest.of(page, 10, Sort.by("transactionDate").descending())
        );
    }

    public void saveTransaction(TransactionRecord transaction, User user) {
        Wallet wallet = getWallet(user);
        validateTransactionBase(transaction);

        adjustWalletBalance(wallet, transaction.getType(), transaction.getAmount(), true);
        
        transaction.setUser(user);
        walletRepository.save(wallet);
        transactionRepository.save(transaction);

        adminService.saveLog(user, "TRANSACTION", "Menambah transaksi: " + transaction.getTitle());

        // Gamification: reward consistent recording so users actually level up.
        // Guarded so a level-system failure can never break transaction creation.
        try {
            levelService.addPoints(user, POINTS_PER_TRANSACTION);
        } catch (Exception ignored) {
            // intentionally ignored — gamification is non-critical
        }
    }

    public void deleteTransaction(Long id, User user) {
        TransactionRecord trx = getOwnedTransaction(id, user);
        Wallet wallet = getWallet(user);

        // FIX: Revert balance dengan validasi saldo minus
        adjustWalletBalance(wallet, trx.getType(), trx.getAmount(), false);

        walletRepository.save(wallet);
        transactionRepository.delete(trx);

        adminService.saveLog(user, "TRANSACTION", "Menghapus transaksi ID: " + id);
    }

    public TransactionRecord findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));
    }

    public void updateTransaction(Long id, TransactionRecord updatedTrx, User user) {
        TransactionRecord existingTrx = getOwnedTransaction(id, user);
        Wallet wallet = getWallet(user);
        validateTransactionBase(updatedTrx);

        // 1. Revert saldo lama
        adjustWalletBalance(wallet, existingTrx.getType(), existingTrx.getAmount(), false);

        // 2. Apply saldo baru
        adjustWalletBalance(wallet, updatedTrx.getType(), updatedTrx.getAmount(), true);

        // 3. Update field (FIX: pake helper method biar rapi)
        existingTrx.setTitle(updatedTrx.getTitle());
        existingTrx.setDescription(updatedTrx.getDescription());
        existingTrx.setCategory(updatedTrx.getCategory());
        existingTrx.setType(updatedTrx.getType());
        existingTrx.setAmount(updatedTrx.getAmount());
        existingTrx.setTransactionDate(updatedTrx.getTransactionDate());

        walletRepository.save(wallet);
        transactionRepository.save(existingTrx);

        adminService.saveLog(user, "TRANSACTION", "Mengupdate transaksi ID: " + id);
    }

    // ==========================================
    // HELPER METHODS (CLEAN CODE)
    // ==========================================
    private Wallet getWallet(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet tidak ditemukan!"));
    }

    private TransactionRecord getOwnedTransaction(Long id, User user) {
        return transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Transaksi tidak valid atau bukan milik Anda!"));
    }

    private void validateTransactionBase(TransactionRecord trx) {
        if (trx.getAmount() == null || trx.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Jumlah transaksi harus lebih dari 0!");
        }
        if (trx.getTransactionDate() != null && trx.getTransactionDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Tanggal transaksi tidak boleh di masa depan!");
        }
    }

    // FIX: Method terpusat buat handle kalkulasi saldo biar gak rawan bug
    private void adjustWalletBalance(Wallet wallet, TransactionType type, BigDecimal amount, boolean isAddingNew) {
        boolean isIncome = type == TransactionType.INCOME;
        // Jika nambah transaksi Income ATAU menghapus transaksi Expense -> Saldo nambah
        if ((isIncome && isAddingNew) || (!isIncome && !isAddingNew)) {
            wallet.setBalance(wallet.getBalance().add(amount));
        } 
        // Jika nambah transaksi Expense ATAU menghapus transaksi Income -> Saldo ngurang
        else {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Saldo dompet tidak cukup untuk melakukan aksi ini!");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }
    }
}