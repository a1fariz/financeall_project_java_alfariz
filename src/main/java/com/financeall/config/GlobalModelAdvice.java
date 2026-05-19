package com.financeall.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    // Otomatis nambahin variabel "requestURI" ke semua file HTML lu
    @ModelAttribute("requestURI")
    public String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }
}