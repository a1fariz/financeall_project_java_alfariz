package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.DebtService;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

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

        model.addAttribute("debts", debtService.getSortedDebts(user.getId(), strategy));
        model.addAttribute("totalDebt", debtService.calculateTotalDebt(user));
        return "user/debt";
    }

    @PostMapping("/add")
    public String addDebt(HttpSession session, @RequestParam String creditor, 
                          @RequestParam BigDecimal totalAmount, @RequestParam String dueDate) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            debtService.createDebt(userService.findById(user.getId()), creditor, totalAmount, dueDate);
        }
        return "redirect:/user/debt";
    }

    @PostMapping("/pay")
    public String payDebt(HttpSession session, @RequestParam Long debtId, @RequestParam BigDecimal amount) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        debtService.payDebt(debtId, amount);
        return "redirect:/user/debt";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDebt(@PathVariable Long id) {
        debtService.delete(id);
        return "redirect:/user/debt";
    }
}