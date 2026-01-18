package com.financeall.repository;

import com.financeall.model.TransactionCategory;
import com.financeall.model.TransactionRecord;
import com.financeall.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {

    // --- BASIC FINDS ---
    Page<TransactionRecord> findByUser(User user, Pageable pageable);
    Page<TransactionRecord> findByUserId(Long userId, Pageable pageable);
    Page<TransactionRecord> findByUserIdAndCategory(Long userId, TransactionCategory category, Pageable pageable);
    Page<TransactionRecord> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // --- AGGREGATION / SUM (DIPAKAI WALLET SERVICE & FINANCIAL HEALTH) ---
    // Gunakan COALESCE untuk menghindari return null
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionRecord t WHERE t.user.id = :userId AND t.type = 'INCOME'")
    BigDecimal sumIncomeByUser(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionRecord t WHERE t.user.id = :userId AND t.type = 'EXPENSE'")
    BigDecimal sumExpenseByUser(@Param("userId") Long userId);

    // --- AGGREGATION GLOBAL (DIPAKAI ADMIN SERVICE) ---
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionRecord t WHERE t.type = 'INCOME'")
    BigDecimal sumAllIncome();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionRecord t WHERE t.type = 'EXPENSE'")
    BigDecimal sumAllExpense();

    // --- CHART / ANALYSIS ---
    @Query("SELECT t.category, SUM(t.amount) FROM TransactionRecord t WHERE t.user.id = :userId AND t.type = 'EXPENSE' GROUP BY t.category")
    List<Object[]> sumExpenseByCategory(@Param("userId") Long userId);

    List<TransactionRecord> findTop5ByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionRecord t WHERE t.user = :user AND t.type = :type AND t.createdAt BETWEEN :start AND :end")
    BigDecimal sumTotalByTypeAndUserAndDateRange(@Param("user") User user, @Param("type") String type, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT t FROM TransactionRecord t WHERE t.amount > 100000000 ORDER BY t.createdAt DESC")
    List<TransactionRecord> findHighValueTransactions();
}