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
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() { return "auth/login"; }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "admin".equals(user.getRole()) ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
        }
        // Pesan gagal login (halaman yang sama)
        model.addAttribute("error", "Username atau Password salah!");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() { return "auth/register"; }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute User user, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes, Model model) {
        // Validasi konfirmasi password
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Konfirmasi password tidak cocok!");
            return "auth/register";
        }

        try {
            userService.register(user);
            // Pesan sukses dikirim ke halaman login
            redirectAttributes.addFlashAttribute("success", "Pendaftaran berhasil! Silakan masuk ke akun Anda.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Terjadi kesalahan saat pendaftaran.");
            return "auth/register";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPage() { return "auth/reset-password"; }

    @PostMapping("/reset-password")
    public String handleReset(@RequestParam String username, 
                              @RequestParam String recoveryPin, 
                              @RequestParam String newPassword, 
                              RedirectAttributes redirectAttributes, 
                              Model model) {
        try {
            userService.resetPassword(username, recoveryPin, newPassword);
            // Pesan sukses dikirim ke halaman login
            redirectAttributes.addFlashAttribute("success", "Password berhasil diubah! Silakan login kembali.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            // Pesan error di halaman reset
            model.addAttribute("error", e.getMessage());
            return "auth/reset-password";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}