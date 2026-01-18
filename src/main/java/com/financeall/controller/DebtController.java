package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.DebtService;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/debt")
@RequiredArgsConstructor
public class DebtController {
    private final DebtService debtService;
    private final UserService userService;

    @GetMapping
    public String listDebts(HttpSession session, @RequestParam(defaultValue = "snowball") String strategy, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        User dbUser = userService.findById(user.getId());
        model.addAttribute("debts", debtService.getSortedDebts(dbUser.getId(), strategy));
        model.addAttribute("totalDebt", debtService.calculateTotalDebt(dbUser));
        return "user/debt";
    }

    @PostMapping("/add")
    public String addDebt(HttpSession session, @RequestParam String creditor, @RequestParam BigDecimal totalAmount, @RequestParam String dueDate) {
        User user = (User) session.getAttribute("user");
        if (user != null) debtService.createDebt(userService.findById(user.getId()), creditor, totalAmount, dueDate);
        return "redirect:/user/debt";
    }
    // Tambahkan di dalam class DebtController
@PostMapping("/pay")
public String payDebt(HttpSession session, @RequestParam Long debtId, @RequestParam BigDecimal amount) {
    User user = (User) session.getAttribute("user");
    if (user == null) return "redirect:/login";
    
    debtService.payDebt(debtId, amount);
    return "redirect:/user/debt";
}
}
