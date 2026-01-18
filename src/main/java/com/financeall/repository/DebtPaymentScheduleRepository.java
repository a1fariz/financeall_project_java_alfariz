package com.financeall.repository;


import com.financeall.model.DebtPaymentSchedule;
import com.financeall.model.DebtItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface DebtPaymentScheduleRepository extends JpaRepository<DebtPaymentSchedule, Long> {
List<DebtPaymentSchedule> findByDebtItem(DebtItem debtItem);
}