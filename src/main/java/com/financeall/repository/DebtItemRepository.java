package com.financeall.repository;

import com.financeall.model.DebtItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtItemRepository extends JpaRepository<DebtItem, Long> {
    List<DebtItem> findByUserId(Long userId);
}
