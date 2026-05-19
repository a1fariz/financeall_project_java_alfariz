package com.financeall.repository;

import com.financeall.model.AdminLog;
import com.financeall.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminLogRepository
        extends JpaRepository<AdminLog, Long> {

    List<AdminLog>
    findTop10ByOrderByCreatedAtDesc();

    List<AdminLog>
    findByUserOrderByCreatedAtDesc(
            User user
    );
}