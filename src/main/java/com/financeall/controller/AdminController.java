package com.financeall.controller;

import com.financeall.model.TransactionRecord;
import com.financeall.model.User;
import com.financeall.model.Announcement;
import com.financeall.model.Level;
import com.financeall.model.AdminLog;
import com.financeall.repository.UserRepository;
import com.financeall.repository.TransactionRecordRepository;
import com.financeall.repository.AnnouncementRepository;
import com.financeall.repository.LevelRepository;
import com.financeall.repository.AdminLogRepository;
import com.financeall.service.AdminService;
import com.financeall.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRepository;
    private final AnnouncementRepository announcementRepository;
    private final LevelRepository levelRepository;
    private final AdminLogRepository adminLogRepository;
    private final AdminService adminService;
    private final UserService userService;
    // ==========================================================
    // 1. ADMIN DASHBOARD
    // ==========================================================
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        long totalUsers = userRepository.count();
        long totalTransactions = transactionRepository.count();
        BigDecimal totalIncome = transactionRepository.sumAllIncome();
        BigDecimal totalExpense = transactionRepository.sumAllExpense();
        
        List<TransactionRecord> suspiciousTransactions = transactionRepository.findHighValueTransactions();
        List<User> latestUsers = userRepository.findTop5ByOrderByIdDesc();
        List<AdminLog> latestLogs = adminLogRepository.findTop10ByOrderByCreatedAtDesc();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalTransactions", totalTransactions);
        model.addAttribute("totalIncome", totalIncome != null ? totalIncome : BigDecimal.ZERO);
        model.addAttribute("totalExpense", totalExpense != null ? totalExpense : BigDecimal.ZERO);
        model.addAttribute("suspiciousTransactions", suspiciousTransactions);
        model.addAttribute("latestUsers", latestUsers);
        model.addAttribute("latestLogs", latestLogs);

        return "admin/dashboard";
    }

    // ==========================================================
    // 2. USER MANAGEMENT (VIEW, BAN, DELETE)
    // ==========================================================
    @GetMapping("/users")
    public String usersPage(@RequestParam(required = false) String keyword, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        List<User> users;
        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userRepository.findByUsernameContainingIgnoreCase(keyword.trim());
        } else {
            users = userRepository.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "admin/users";
    }

    @PostMapping("/users/ban/{id}")
    public String banUser(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        userRepository.findById(id).ifPresent(user -> {
            user.setBanned(!user.isBanned()); // Toggle status ban
            userRepository.save(user);
        });
        return "redirect:/admin/users";
    }

    // Di AdminController.java:
@PostMapping("/users/delete/{id}")
public String deleteUser(@PathVariable Long id, HttpSession session) {
    User admin = (User) session.getAttribute("user");
    if (admin == null) return "redirect:/login";
    
    adminService.deleteUser(id, admin); // Gunakan service, JANGAN repo
    return "redirect:/admin/users";
}

    // ==========================================================
    // 3. SYSTEM ANALYSIS
    // ==========================================================
    @GetMapping("/analysis")
    public String analysisPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        BigDecimal totalIncome = transactionRepository.sumAllIncome();
        BigDecimal totalExpense = transactionRepository.sumAllExpense();
        BigDecimal globalBalance = (totalIncome != null ? totalIncome : BigDecimal.ZERO)
                .subtract(totalExpense != null ? totalExpense : BigDecimal.ZERO);

        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalIncome", totalIncome != null ? totalIncome : BigDecimal.ZERO);
        model.addAttribute("totalExpense", totalExpense != null ? totalExpense : BigDecimal.ZERO);
        model.addAttribute("globalBalance", globalBalance);

        return "admin/analysis";
    }

    // ==========================================================
    // 4. BROADCAST ARTICLES / ANNOUNCEMENTS
    // ==========================================================
    @GetMapping("/articles")
    public String articlesPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        model.addAttribute("articles", announcementRepository.findAll());
        return "admin/articles";
    }

    @PostMapping("/articles/add")
    public String addArticle(@RequestParam String title, @RequestParam String content, HttpSession session) {
        User admin = (User) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        Announcement announcement = Announcement.builder()
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        announcementRepository.save(announcement);
        return "redirect:/admin/articles";
    }

    @PostMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        announcementRepository.deleteById(id);
        return "redirect:/admin/articles";
    }

    // ==========================================================
    // 5. PROGRESSION LEVELS
    // ==========================================================
    @GetMapping("/levels")
    public String levelsPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        model.addAttribute("alllevels", levelRepository.findAll());
        return "admin/levels";
    }

    @PostMapping("/levels/add")
    public String saveOrUpdateLevel(@RequestParam(required = false) Long id, 
                                    @RequestParam String name, 
                                    @RequestParam Integer requiredPoints, 
                                    HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        Level level = Level.builder()
                .id(id)
                .name(name)
                .requiredPoints(requiredPoints)
                .build();

        levelRepository.save(level);
        return "redirect:/admin/levels";
    }
    @PostMapping("/levels/delete/{id}")
    public String deleteLevel(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        // Fitur hapus level
        levelRepository.deleteById(id);
        return "redirect:/admin/levels";
    }

    // ==========================================================
    // 6. ACTIVITY LOGS
    // ==========================================================
    @GetMapping("/logs")
    public String logsPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        model.addAttribute("allLogs", adminLogRepository.findAll());
        return "admin/logs";
    }

    // ==========================================================
    // 7. ADMIN PROFILE
    // ==========================================================
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        model.addAttribute("adminUser", admin);
        return "admin/profile";
    }

    @PostMapping("/profile/update")
    public String updateAdminProfile(@RequestParam String username,
                                     @RequestParam String email,
                                     @RequestParam(required = false) String password,
                                     HttpSession session,
                                     RedirectAttributes ra) {
        User admin = (User) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        try {
            userService.updateAdminProfile(admin.getId(), username, email, password);
            // Refresh session so navbar/profile reflect changes
            session.setAttribute("user", userService.findById(admin.getId()));
            return "redirect:/admin/profile?success";
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/admin/profile?error";
        }
    }
}