package com.financeall.controller;

import com.financeall.service.FIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/user/fi")
@RequiredArgsConstructor
public class FIController {
    private final FIService fiService;

    @GetMapping
    public String showFI() {
        return "user/fi";
    }

    @PostMapping("/calculate")
    public String calculateFI(@RequestParam BigDecimal monthlyExpense, 
                              @RequestParam BigDecimal currentAssets,
                              @RequestParam(defaultValue = "4.0") BigDecimal withdrawalRate, 
                              Model model) {
        // Memanggil service dengan parameter lengkap
        model.addAttribute("result", fiService.performCalculation(monthlyExpense, withdrawalRate, currentAssets));
        return "user/fi";
    }
}