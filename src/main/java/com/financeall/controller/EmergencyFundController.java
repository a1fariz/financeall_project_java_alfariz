package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.EmergencyFundService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/user/emergency")
@RequiredArgsConstructor
public class EmergencyFundController {
    private final EmergencyFundService emergencyFundService;

    @GetMapping
    public String viewEmergencyFund(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        // Ganti nama atribut dari "emergencyFund" menjadi "fund" agar sesuai dengan HTML
        model.addAttribute("fund", emergencyFundService.getOrCreateFund(user));
        return "user/emergency";
    }

    @PostMapping("/set-target")
    public String setTarget(@RequestParam("target") BigDecimal targetAmount, // Sesuaikan @RequestParam dengan name="target" di HTML
                            HttpSession session, 
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        try {
            emergencyFundService.updateTarget(user, targetAmount);
            redirectAttributes.addFlashAttribute("successMsg", "Target berhasil diperbarui!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Gagal: " + e.getMessage());
        }
        return "redirect:/user/emergency";
    }

    // TAMBAHKAN INI: Menangani form "Add to Fund" agar tidak 404
    @PostMapping("/update")
    public String addFund(@RequestParam("amount") BigDecimal amount, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        try {
            emergencyFundService.addFund(user, amount);
            ra.addFlashAttribute("successMsg", "Saldo berhasil ditambah!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/user/emergency";
    }
}

