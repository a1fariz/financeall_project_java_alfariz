package com.financeall.repository;

import com.financeall.model.User;
import com.financeall.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
    
    // Query untuk Admin Dashboard
    @Query("SELECT SUM(w.balance) FROM Wallet w")
    BigDecimal sumAllBalances();
}