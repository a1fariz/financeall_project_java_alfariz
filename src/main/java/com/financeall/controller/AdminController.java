package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // Sinkronkan session dengan Header
    private User getLoggedAdmin(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", adminService.countUsers());
        model.addAttribute("globalStats", adminService.getGlobalStats());
        return "admin/dashboard";
    }


@PostMapping("/broadcast") // Ini adalah "pintu" yang dicari form Anda
public String handleBroadcast(@RequestParam String title, @RequestParam String message, HttpSession session) {
    
    // 1. Ambil data admin yang sedang login dari session
    User admin = (User) session.getAttribute("user");
    
    // 2. Simpan pesan ke database (menggunakan service yang sudah ada)
    // Di sistem Anda, broadcast disimpan ke tabel Announcement/Article
    adminService.saveArticle(title, message, admin);
    
    // 3. INI KUNCINYA: Jangan cari broadcast.html, tapi suruh balik ke dashboard
    // 'redirect' artinya menyuruh browser pindah ke URL /admin/dashboard
    return "redirect:/admin/dashboard?success=true";
}

    @GetMapping("/articles")
    public String listArticles(Model model) {
        model.addAttribute("articles", adminService.getAllArticles());
        return "admin/articles";
    }

    @PostMapping("/articles/add")
    public String addArticle(@RequestParam String title, @RequestParam String content, HttpSession session) {
        if (title.isBlank() || content.isBlank()) return "redirect:/admin/articles?error=empty";
        adminService.saveArticle(title, content, getLoggedAdmin(session));
        return "redirect:/admin/articles?success=true";
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        adminService.deleteArticle(id, getLoggedAdmin(session));
        return "redirect:/admin/articles";
    }

    @GetMapping("/analysis")
    public String analysis(Model model) {
        model.addAttribute("stats", adminService.getGlobalStats());
        model.addAttribute("totalUsers", adminService.countUsers());
        return "admin/analysis";
    }

    @GetMapping("/profile")
    public String editProfile(HttpSession session, Model model) {
        User admin = getLoggedAdmin(session);
        if (admin == null) return "redirect:/login";
        model.addAttribute("adminUser", admin);
        return "admin/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String username, @RequestParam String email, 
                                @RequestParam(required = false) String password, HttpSession session) {
        User current = getLoggedAdmin(session);
        adminService.updateAdminProfile(current.getId(), username, email, password, current);
        
        current.setUsername(username);
        current.setEmail(email);
        session.setAttribute("user", current);
        
        return "redirect:/admin/profile?success=true";
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam(required = false) String keyword, 
                            @RequestParam(defaultValue = "username") String sortBy, Model model) {
        model.addAttribute("users", adminService.findAllUsers(keyword, sortBy));
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentSort", sortBy);
        return "admin/users";
    }

    @PostMapping("/users/ban/{id}")
    public String toggleBan(@PathVariable Long id, HttpSession session) {
        adminService.toggleUserStatus(id, getLoggedAdmin(session));
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        adminService.deleteUser(id, getLoggedAdmin(session));
        return "redirect:/admin/users";
    }

    @GetMapping("/logs")
    public String logs(Model model) {
        model.addAttribute("allLogs", adminService.getAllLogs());
        return "admin/logs";
    }

   @GetMapping("/levels")
public String levels(Model model) {
    model.addAttribute("levels", adminService.findAllLevels());
    return "admin/levels";
}

// Di AdminController.java

@PostMapping("/levels/save")
public String saveLevel(@RequestParam(required = false) Long id, 
                        @RequestParam String name, 
                        @RequestParam(required = false) Integer requiredPoints, // Gunakan Integer agar aman dari null
                        HttpSession session) {
    
    // Validasi sederhana agar tidak 500 jika points kosong
    int points = (requiredPoints == null) ? 0 : requiredPoints;
    
    User admin = (User) session.getAttribute("user");
    adminService.saveLevel(id, name, points, admin);
    
    return "redirect:/admin/levels?success=true";
}

@GetMapping("/levels/delete/{id}")
public String deleteLevel(@PathVariable Long id, HttpSession session) {
    adminService.deleteLevel(id, getLoggedAdmin(session));
    return "redirect:/admin/levels";
}
}