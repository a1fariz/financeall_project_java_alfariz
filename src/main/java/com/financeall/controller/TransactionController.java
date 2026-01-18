package com.financeall.controller;

import com.financeall.model.TransactionCategory;
import com.financeall.model.TransactionRecord;
import com.financeall.model.User;
import com.financeall.service.TransactionService;
import com.financeall.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/user/transaction") // Konsisten dengan Header
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

  @GetMapping
    public String listTransactions(HttpSession session, @RequestParam(defaultValue = "0") int page, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("transactions", transactionService.findUserTransactions(userService.findById(user.getId()), page));
        model.addAttribute("categories", TransactionCategory.values());
        return "user/transaction";
    }

    // PERBAIKAN: Ubah mapping agar tidak duplikat dan tambahkan penanganan error
    @PostMapping("/add") 
    public String addTransaction(@ModelAttribute TransactionRecord transaction, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        try {
            transactionService.saveTransaction(transaction, user);
            redirectAttributes.addFlashAttribute("successMsg", "Transaksi berhasil ditambahkan!");
        } catch (RuntimeException e) {
            // Menangkap error "Saldo tidak cukup" dari Service
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        
        return "redirect:/user/transaction";
    }

   @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        // PERBAIKAN: Gunakan .orElseThrow atau .get() jika service mengembalikan Optional
        // agar Thymeleaf bisa membaca property seperti ${transaction.id}
        TransactionRecord transaction = transactionService.findById(id); 
        // Pastikan service Anda sudah mengembalikan TransactionRecord, bukan Optional<TransactionRecord>
        // Jika service mengembalikan Optional, gunakan: transactionService.findById(id).orElse(null);

        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", TransactionCategory.values());
        return "user/transaction-edit"; // Pastikan file ada di templates/user/transaction-edit.html
    }

    @PostMapping("/update")
    public String updateTransaction(@ModelAttribute TransactionRecord transaction, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        try {
            transactionService.updateTransaction(transaction.getId(), transaction, user);
            redirectAttributes.addFlashAttribute("successMsg", "Transaksi berhasil diperbarui!");
            return "redirect:/user/transaction";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            // PERBAIKAN: Pastikan ID tidak null saat redirect balik ke halaman edit
            if (transaction.getId() != null) {
                return "redirect:/user/transaction/edit/" + transaction.getId();
            }
            return "redirect:/user/transaction";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            transactionService.deleteTransaction(id, userService.findById(user.getId()));
            redirectAttributes.addFlashAttribute("successMsg", "Transaksi berhasil dihapus!");
        }
        return "redirect:/user/transaction";
    }

    
}
