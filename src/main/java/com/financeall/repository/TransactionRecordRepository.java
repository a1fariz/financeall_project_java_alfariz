package com.financeall.repository;

import com.financeall.model.TransactionCategory;
import com.financeall.model.TransactionRecord;
import com.financeall.model.TransactionType;
import com.financeall.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {

    Page<TransactionRecord> findByUser(User user, Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM TransactionRecord t WHERE t.user.id = :userId AND t.type = 'INCOME'")
    BigDecimal sumIncomeByUser(@Param("userId") Long userId);

    @Query("SELECT SUM(t.amount) FROM TransactionRecord t WHERE t.user.id = :userId AND t.type = 'EXPENSE'")
    BigDecimal sumExpenseByUser(@Param("userId") Long userId);

    @Query("SELECT SUM(t.amount) FROM TransactionRecord t WHERE t.type = 'INCOME'")
    BigDecimal sumAllIncome();

    @Query("SELECT SUM(t.amount) FROM TransactionRecord t WHERE t.type = 'EXPENSE'")
    BigDecimal sumAllExpense();

    List<TransactionRecord> findTop5ByUserOrderByTransactionDateDesc(User user);

    @Query("SELECT SUM(t.amount) FROM TransactionRecord t WHERE t.user = :user AND t.type = :type AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumTotalByTypeAndUserAndDateRange(@Param("user") User user, @Param("type") TransactionType type, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT t FROM TransactionRecord t WHERE t.amount > 100000000 ORDER BY t.transactionDate DESC")
    List<TransactionRecord> findHighValueTransactions();
}