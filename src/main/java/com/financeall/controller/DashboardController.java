package com.financeall.controller;

import com.financeall.model.TransactionRecord;
import com.financeall.model.User;
import com.financeall.model.Wallet;
import com.financeall.repository.TransactionRecordRepository;
import com.financeall.repository.WalletRepository;
import com.financeall.service.AnnouncementService;
import com.financeall.service.UserService;
import com.financeall.service.WalletService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class DashboardController {

    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private final TransactionRecordRepository transactionRepository;
    private final AnnouncementService announcementService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        // FIX: Re-fetch user from DB to avoid stale/detached session object
        User user = userService.findById(sessionUser.getId());
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        // FIX: Inisialisasi proper menghindari nullable violation
        Wallet wallet = walletRepository.findByUser(user)
                .orElseGet(() -> {
                    Wallet newWallet = Wallet.builder()
                            .user(user)
                            .balance(BigDecimal.ZERO)
                            .monthlyLimit(BigDecimal.ZERO) 
                            .amount(BigDecimal.ZERO)
                            .build();
                    return walletRepository.save(newWallet);
                });

        Map<String, Object> monthlyStats = walletService.getMonthlyStats(user);
        List<TransactionRecord> recentTransactions = transactionRepository.findTop5ByUserOrderByTransactionDateDesc(user);

        model.addAttribute("wallet", wallet);
        model.addAttribute("recentTransactions", recentTransactions);
        model.addAttribute("user", user);
        model.addAttribute("announcements", announcementService.findAllActive());

        model.addAttribute("totalIncome", getBigDecimalSafely(monthlyStats, "income"));
        model.addAttribute("totalExpense", getBigDecimalSafely(monthlyStats, "expense"));
        model.addAttribute("cashflow", getBigDecimalSafely(monthlyStats, "cashflow"));

        return "user/dashboard";
    }

    private BigDecimal getBigDecimalSafely(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return BigDecimal.valueOf(((Number) value).doubleValue());
        return BigDecimal.ZERO;
    }
}