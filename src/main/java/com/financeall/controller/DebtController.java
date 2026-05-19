package com.financeall.controller;

import com.financeall.model.DebtItem;
import com.financeall.model.User;
import com.financeall.service.DebtService;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/user/debt")
@RequiredArgsConstructor
public class DebtController {
    private final DebtService debtService;
    private final UserService userService;

    @GetMapping
    public String listDebts(HttpSession session, @RequestParam(defaultValue = "snowball") String strategy, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        model.addAttribute("debts", debtService.getSortedDebts(user.getId(), strategy));
        model.addAttribute("totalDebt", debtService.calculateTotalDebt(user));
        return "user/debt";
    }

    @PostMapping("/add")
    public String addDebt(HttpSession session, @RequestParam String creditor, 
                          @RequestParam BigDecimal totalAmount, @RequestParam String dueDate,
                          RedirectAttributes ra) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        debtService.createDebt(user, creditor, totalAmount, dueDate);
        ra.addFlashAttribute("successMsg", "Hutang berhasil ditambahkan!");
        return "redirect:/user/debt";
    }

    @PostMapping("/pay")
    public String payDebt(HttpSession session, @RequestParam Long debtId, @RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";
    
        try {
            debtService.payDebt(debtId, amount);
            redirectAttributes.addFlashAttribute("successMsg", "Pembayaran berhasil!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/user/debt";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDebt(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        try {
            debtService.deleteByIdAndUser(id, sessionUser.getId());
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/user/debt";
    }
}