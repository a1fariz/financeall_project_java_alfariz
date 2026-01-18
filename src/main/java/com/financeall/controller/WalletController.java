package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/user/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final UserService userService;

    @GetMapping
    public String showWallet(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        // Pastikan model wallet dikirim agar tidak Error 500
        model.addAttribute("wallet", userService.getOrCreateWallet(userService.findById(user.getId())));
        return "user/wallet";
    }
}

