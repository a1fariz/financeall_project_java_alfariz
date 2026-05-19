package com.financeall.controller;

import com.financeall.model.FIRecord;
import com.financeall.model.User;
import com.financeall.repository.FIRecordRepository;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class FIController {

    private final FIRecordRepository fiRecordRepository;
    private final UserService userService;

    @GetMapping("/fi")
    public String fiPage(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        List<FIRecord> records = fiRecordRepository.findTop5ByUserOrderByCreatedAtDesc(user);
        model.addAttribute("records", records);
        
        // FIX: Antisipasi Error 500 dengan menyuntikkan nilai awal agar format desimal tidak membaca objek null
        model.addAttribute("fiTarget", BigDecimal.ZERO);
        model.addAttribute("estimatedYears", 0);
        model.addAttribute("futureValue", BigDecimal.ZERO);
        model.addAttribute("hasResult", false);

        return "user/fi";
    }

   @PostMapping("/fi/calculate")
    public String calculateFI(
            @RequestParam BigDecimal monthlyExpense,
            @RequestParam BigDecimal currentInvestment,
            @RequestParam BigDecimal monthlyInvestment,
            @RequestParam BigDecimal annualReturn,
            HttpSession session,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        try {
            // 1. Guard Clause: Validasi Input (Cegah nilai negatif)
            if (monthlyExpense.compareTo(BigDecimal.ZERO) <= 0 || annualReturn.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Nilai pengeluaran dan bunga harus positif!");
            }

            // 2. Kalkulasi Target
            BigDecimal fiTarget = monthlyExpense.multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(25));
            
            // 3. Handling Bunga 0% (Cegah DivisionByZero atau logika bunga rusak)
            BigDecimal current = currentInvestment;
            int years = 0;

            if (annualReturn.compareTo(BigDecimal.ZERO) == 0) {
                // Jika bunga 0%, gunakan logika linear sederhana
                BigDecimal totalNeeded = fiTarget.subtract(currentInvestment);
                if (monthlyInvestment.compareTo(BigDecimal.ZERO) > 0) {
                    years = totalNeeded.divide(monthlyInvestment.multiply(BigDecimal.valueOf(12)), java.math.RoundingMode.UP).intValue();
                } else {
                    years = (current.compareTo(fiTarget) < 0) ? 100 : 0; 
                }
            } else {
                // Logika Bunga Majemuk
                BigDecimal monthlyRate = annualReturn.divide(BigDecimal.valueOf(100), 10, java.math.RoundingMode.HALF_UP)
                        .divide(BigDecimal.valueOf(12), 10, java.math.RoundingMode.HALF_UP);

                while (current.compareTo(fiTarget) < 0 && years < 100) {
                    for (int i = 0; i < 12; i++) {
                        current = current.multiply(BigDecimal.ONE.add(monthlyRate)).add(monthlyInvestment);
                    }
                    years++;
                }
            }

            // 4. Save Record
            FIRecord record = FIRecord.builder()
                    .user(user)
                    .monthlyExpense(monthlyExpense)
                    .currentInvestment(currentInvestment)
                    .monthlyInvestment(monthlyInvestment)
                    .annualReturn(annualReturn)
                    .fiTarget(fiTarget)
                    .estimatedYears(years)
                    .build();
            fiRecordRepository.save(record);

            model.addAttribute("fiTarget", fiTarget);
            model.addAttribute("estimatedYears", years);
            model.addAttribute("futureValue", current);
            model.addAttribute("hasResult", true);

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("hasResult", false);
        }

        model.addAttribute("records", fiRecordRepository.findTop5ByUserOrderByCreatedAtDesc(user));
        return "user/fi";
    }
    
}