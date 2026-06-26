package com.financeall.controller;

import com.financeall.dto.RegisterDTO;
import com.financeall.model.User;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // =========================
    // LOGIN
    // =========================
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.login(username.trim(), password.trim());

            if(user == null) {
                model.addAttribute("error", "Username atau password salah");
                return "auth/login";
            }

            if(user.isBanned()) {
                model.addAttribute("error", "Akun Anda telah dibanned oleh Admin");
                return "auth/login";
            }

            session.setAttribute("user", user);

            String role = (user.getRole() != null) ? user.getRole().toUpperCase() : "USER";

            if ("ADMIN".equals(role)) {
                return "redirect:/admin/dashboard";
            }
return "redirect:/user/dashboard";

        } catch (Exception e) {
            model.addAttribute("error", "Terjadi kegagalan sistem: " + e.getMessage());
            return "auth/login";
        }
    }

    // =========================
    // REGISTER (FIX: TADI HILANG)
    // =========================
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/register";
    }
@PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
                                  BindingResult bindingResult, Model model) {
        // Bean validation (@NotBlank/@Email/@Size/@Pattern on RegisterDTO) — render field errors on the form
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            // 1. Cek Password Match
            if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
                throw new RuntimeException("Password dan Konfirmasi Password tidak cocok!");
            }

            // 2. Cek Username Duplikat
            if (userService.findByUsername(registerDTO.getUsername().trim()) != null) {
                throw new RuntimeException("Username sudah digunakan, silakan pilih yang lain!");
            }

            // 3. Cek Email Duplikat (Ini yang sangat penting)
            if (userService.findByEmail(registerDTO.getEmail().trim()) != null) {
                throw new RuntimeException("Alamat email sudah terdaftar!");
            }
            
            // 4. Jika lolos semua, buat akun baru
            User newUser = User.builder()
                    .fullName(registerDTO.getFullName())
                    .username(registerDTO.getUsername())
                    .email(registerDTO.getEmail())
                    .password(registerDTO.getPassword()) 
                    .recoveryPin(registerDTO.getRecoveryPin())
                    .build();

            userService.register(newUser);
            model.addAttribute("success", "Akun berhasil dibuat! Silakan login.");
            return "auth/login";

        } catch (Exception e) {
            model.addAttribute("error", "Gagal mendaftar: " + e.getMessage());
            return "auth/register";
        }
    }

    // =========================
    // RESET PASSWORD
    // =========================
    @GetMapping("/reset-password")
    public String showResetPage() {
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String handleReset(@RequestParam String username, @RequestParam String recoveryPin, @RequestParam String newPassword, RedirectAttributes redirectAttributes, Model model) {
        try {
            userService.resetPassword(username.trim(), recoveryPin.trim(), newPassword.trim());
            redirectAttributes.addFlashAttribute("success", "Password berhasil diubah! Silakan login kembali.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/reset-password";
        }
    }

    // =========================
    // LOGOUT
    // =========================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}