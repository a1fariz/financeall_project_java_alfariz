package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        try {
            userService.updateProfile(user, updatedUser);
            // Refresh session data biar nama di Navbar ikut berubah
            session.setAttribute("user", userService.findById(user.getId())); 
            redirectAttributes.addFlashAttribute("successMsg", "Profil berhasil diperbarui! ✅");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Gagal memperbarui profil: " + e.getMessage());
        }
        return "redirect:/user/profile";
    }
}