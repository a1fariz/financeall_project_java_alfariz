package com.financeall.controller;

import com.financeall.model.Level;
import com.financeall.model.User;
import com.financeall.model.UserLevelProgress;
import com.financeall.service.LevelService;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LevelController {
    private final LevelService levelService;
    private final UserService userService;

    @GetMapping("/user/level")
    public String userLevel(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        // Tarik data
        UserLevelProgress progress = levelService.getUserLevelData(user);
        List<Level> allLevels = levelService.findAll();

        // FIX: Kalkulasi Progress Percentage (XP Bar)
        Level currentLevel = progress.getLevel();
        int currentPoints = progress.getCurrentPoints();
        int nextLevelPoints = 0;

        for (Level lvl : allLevels) {
            if (lvl.getRequiredPoints() > currentPoints) {
                nextLevelPoints = lvl.getRequiredPoints();
                break;
            }
        }

        double progressPercentage = 100.0;
        if (nextLevelPoints > 0) {
            int prevLevelPoints = currentLevel != null ? currentLevel.getRequiredPoints() : 0;
            int pointsNeeded = nextLevelPoints - prevLevelPoints;
            int pointsEarned = currentPoints - prevLevelPoints;
            progressPercentage = ((double) pointsEarned / pointsNeeded) * 100;
            if(progressPercentage > 100) progressPercentage = 100;
            if(progressPercentage < 0) progressPercentage = 0;
        }

        // Kirim semua variabel ke HTML
        model.addAttribute("progress", progress);
        model.addAttribute("allLevels", allLevels);
        model.addAttribute("progressPercentage", progressPercentage);
        model.addAttribute("nextLevelPoints", nextLevelPoints);

        return "user/level";
    }
}