package com.financeall.repository;

import com.financeall.model.FIRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FIRecordRepository extends JpaRepository<FIRecord, Long> {
    List<FIRecord> findByUserId(Long userId);
}
