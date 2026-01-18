package com.financeall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // PERBAIKAN: Hapus "/auth", sesuaikan dengan AuthController
        return "redirect:/login"; 
    }
}