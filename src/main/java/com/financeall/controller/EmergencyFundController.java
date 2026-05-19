package com.financeall.controller;

import com.financeall.model.EmergencyFund;
import com.financeall.model.User;
import com.financeall.repository.EmergencyFundRepository;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class EmergencyFundController {

    private final EmergencyFundRepository emergencyFundRepository;
    private final UserService userService;

    @GetMapping("/emergency")
    public String emergencyFund(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        EmergencyFund emergencyFund = emergencyFundRepository.findByUser(user)
                .orElseGet(() -> {
                    EmergencyFund fund = EmergencyFund.builder()
                            .user(user)
                            .targetAmount(BigDecimal.valueOf(10000000)) // Default target
                            .currentAmount(BigDecimal.ZERO)
                            .monthlySaving(BigDecimal.valueOf(500000))
                            .build();
                    return emergencyFundRepository.save(fund);
                });

        double progress = 0;
        if (emergencyFund.getTargetAmount() != null && emergencyFund.getTargetAmount().compareTo(BigDecimal.ZERO) > 0) {
            progress = emergencyFund.getCurrentAmount()
                    .divide(emergencyFund.getTargetAmount(), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();
        }

        int estimatedMonths = 0;
        if (emergencyFund.getMonthlySaving() != null && emergencyFund.getMonthlySaving().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal remaining = emergencyFund.getTargetAmount().subtract(emergencyFund.getCurrentAmount());
            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                estimatedMonths = remaining.divide(emergencyFund.getMonthlySaving(), 0, RoundingMode.UP).intValue();
            }
        }

        model.addAttribute("emergencyFund", emergencyFund);
        model.addAttribute("progress", progress);
        model.addAttribute("estimatedMonths", estimatedMonths);

        return "user/emergency";
    }

    // FIX: Method penangkap request update dana darurat
    @PostMapping("/emergency/update")
    public String updateEmergencyFund(@RequestParam BigDecimal amount, HttpSession session, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        try {
            EmergencyFund fund = emergencyFundRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Data dana darurat tidak ditemukan"));
            
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Nominal penambahan harus lebih dari 0!");
            }

            fund.setCurrentAmount(fund.getCurrentAmount().add(amount));
            emergencyFundRepository.save(fund);
            
            redirectAttributes.addFlashAttribute("successMsg", "Dana darurat berhasil diperbarui!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/user/emergency";
    }
}