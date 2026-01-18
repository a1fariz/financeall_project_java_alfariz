package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", userService.findById(user.getId()));
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userService.updateProfile(user, updatedUser);
            session.setAttribute("user", user); // Update session data
        }
        return "redirect:/user/profile?success";
    }
}