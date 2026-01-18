package com.financeall.repository;

import com.financeall.model.EmergencyFund;
import com.financeall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmergencyFundRepository extends JpaRepository<EmergencyFund, Long> {
    // Metode ini WAJIB ADA agar EmergencyFundService tidak error
    Optional<EmergencyFund> findByUser(User user);
}