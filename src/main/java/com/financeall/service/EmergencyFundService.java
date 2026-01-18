package com.financeall.service;

import com.financeall.model.EmergencyFund;
import com.financeall.model.User;
import com.financeall.repository.EmergencyFundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EmergencyFundService {
    private final EmergencyFundRepository emergencyFundRepository;

    public EmergencyFund getOrCreateFund(User user) {
        return emergencyFundRepository.findByUser(user)
                .orElseGet(() -> {
                    EmergencyFund newFund = new EmergencyFund();
                    newFund.setUser(user);
                    newFund.setCurrentAmount(BigDecimal.ZERO);
                    newFund.setTargetAmount(BigDecimal.ZERO);
                    return emergencyFundRepository.save(newFund);
                });
    }

    @Transactional
    public void updateTarget(User user, BigDecimal targetAmount) {
        EmergencyFund fund = getOrCreateFund(user);
        fund.setTargetAmount(targetAmount);
        emergencyFundRepository.save(fund);
    }
    @Transactional
public void addFund(User user, BigDecimal amount) {
    EmergencyFund fund = getOrCreateFund(user);
    // Pastikan currentAmount tidak null sebelum dijumlahkan
    BigDecimal current = fund.getCurrentAmount() != null ? fund.getCurrentAmount() : BigDecimal.ZERO;
    fund.setCurrentAmount(current.add(amount));
    emergencyFundRepository.save(fund);
}
}