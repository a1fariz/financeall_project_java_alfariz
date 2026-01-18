package com.financeall.service;

import com.financeall.model.*;
import com.financeall.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRecordRepository transactionRepository;
    private final WalletRepository walletRepository;
    
    // TAMBAHKAN INI: Inject service yang dibutuhkan agar tidak error "cannot be resolved"
    private final WalletService walletService;
    private final AdminService adminService;

    public Page<TransactionRecord> findUserTransactions(User user, int page) {
        return transactionRepository.findByUser(user, PageRequest.of(page, 10, Sort.by("createdAt").descending()));
    }
    public void saveTransaction(TransactionRecord transaction, User user) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet tidak ditemukan!"));

        // Validasi input dasar
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Jumlah transaksi harus lebih dari 0!");
        }

        transaction.setUser(user);
        transaction.setWallet(wallet);
        
        // Pastikan waktu tercatat jika tidak diisi dari form
        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(LocalDateTime.now());
        }

        // Logika update saldo
        if ("INCOME".equals(transaction.getType())) {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        } else if ("EXPENSE".equals(transaction.getType())) {
            if (wallet.getBalance().compareTo(transaction.getAmount()) < 0) {
                throw new RuntimeException("Saldo tidak cukup!"); // Dilempar ke Controller
            }
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        }

        walletRepository.save(wallet);
        transactionRepository.save(transaction);
        
        adminService.saveLog(user, "TRANSACTION", "Menambah transaksi: " + transaction.getDescription());
    }

    // Tetap pertahankan createTransaction jika masih ada controller lama yang memanggilnya
    public void createTransaction(User user, BigDecimal amount, String type, TransactionCategory category, String dateStr, String desc) {
        TransactionRecord trx = TransactionRecord.builder()
                .amount(amount).type(type).category(category).description(desc).build();
        
        if (dateStr != null && !dateStr.isEmpty()) {
            trx.setCreatedAt(LocalDate.parse(dateStr).atStartOfDay());
        }
        
        saveTransaction(trx, user);
    }

    public void deleteTransaction(Long id, User user) {
        TransactionRecord trx = transactionRepository.findById(id)
            .filter(t -> t.getUser().getId().equals(user.getId()))
            .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));
            
        Wallet wallet = trx.getWallet();
        if ("INCOME".equals(trx.getType())) {
            wallet.setBalance(wallet.getBalance().subtract(trx.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(trx.getAmount()));
        }
        walletRepository.save(wallet);
        transactionRepository.delete(trx);
    }
    public TransactionRecord findById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));
    }

    @Transactional
    public void updateTransaction(Long id, TransactionRecord updatedTrx, User user) {
        TransactionRecord existingTrx = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan!"));

        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet tidak ditemukan!"));

        // 1. Kembalikan saldo lama (Revert)
        if ("INCOME".equals(existingTrx.getType())) {
            wallet.setBalance(wallet.getBalance().subtract(existingTrx.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(existingTrx.getAmount()));
        }

        // 2. Validasi saldo untuk nilai baru (jika pengeluaran)
        if ("EXPENSE".equals(updatedTrx.getType()) && wallet.getBalance().add(BigDecimal.ZERO).compareTo(updatedTrx.getAmount()) < 0) {
             // Revert balik jika gagal agar saldo tidak rusak (rollback ditangani @Transactional)
             throw new RuntimeException("Saldo tidak cukup untuk perubahan ini!");
        }

        // 3. Update field transaksi
        existingTrx.setAmount(updatedTrx.getAmount());
        existingTrx.setType(updatedTrx.getType());
        existingTrx.setCategory(updatedTrx.getCategory());
        existingTrx.setDescription(updatedTrx.getDescription());

        // 4. Terapkan saldo baru
        if ("INCOME".equals(updatedTrx.getType())) {
            wallet.setBalance(wallet.getBalance().add(updatedTrx.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().subtract(updatedTrx.getAmount()));
        }

        walletRepository.save(wallet);
        transactionRepository.save(existingTrx);
        adminService.saveLog(user, "TRANSACTION", "Mengupdate transaksi ID: " + id);
    }

}