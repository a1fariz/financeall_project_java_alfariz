package com.financeall.controller;

import com.financeall.model.User;
import com.financeall.service.LevelService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LevelController {
    private final LevelService levelService;

    // Method adminLevels DIHAPUS dari sini untuk menghindari Ambiguous Mapping

    @GetMapping("/user/level")
    public String userLevel(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            model.addAttribute("levelData", levelService.getUserLevelData(user));
        }
        return "user/level";
    }
}