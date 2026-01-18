package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final AdminService adminService;
    private final LevelService levelService;
    private final DebtService debtService;
    private final EmergencyFundService emergencyFundService;

  @GetMapping("/user/dashboard")
public String userDashboard(HttpSession session, Model model) {
    // 1. Ambil user dari session
    User sessionUser = (User) session.getAttribute("user");
    
    // 2. Validasi session dan ID (Menghindari warning Null type safety)
    if (sessionUser == null || sessionUser.getId() == null) {
        return "redirect:/login";
    }
    
    // 3. Refresh data user dari DB. Pastikan ID tidak null untuk findById
    Long userId = sessionUser.getId();
    User user = userService.findById(userId);
    
    // 4. Handle jika user tidak ditemukan (misal: user dihapus admin tapi session masih ada)
    if (user == null) {
        session.invalidate();
        return "redirect:/login";
    }
    
    // 5. Masukkan data ke model dengan aman
    model.addAttribute("user", user);
    model.addAttribute("wallet", userService.getOrCreateWallet(user));
    model.addAttribute("level", levelService.getUserLevel(user));
    model.addAttribute("emergencyFund", emergencyFundService.getOrCreateFund(user));
    model.addAttribute("totalDebt", debtService.calculateTotalDebt(user));
    
    model.addAttribute("announcements", announcementService.findAllActive());
    model.addAttribute("articles", adminService.getLatestArticles());
    
    return "user/dashboard";
}
}