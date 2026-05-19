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

@Controller
@RequestMapping("/user/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping
    public String listTransactions(HttpSession session, @RequestParam(defaultValue = "0") int page, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        model.addAttribute("transactions", transactionService.findUserTransactions(user, page));
        model.addAttribute("categories", TransactionCategory.values());
        return "user/transaction";
    }

    @PostMapping("/add") 
    public String addTransaction(@ModelAttribute TransactionRecord transaction, HttpSession session, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        try {
            transactionService.saveTransaction(transaction, user);
            redirectAttributes.addFlashAttribute("successMsg", "Transaksi berhasil ditambahkan!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/user/transaction";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }
    
        try {
            TransactionRecord transaction = transactionService.findById(id);
        
            // SECURITY CHECK (PENCEGAHAN IDOR)
            if (!transaction.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Akses ditolak: Ini bukan transaksi Anda!");
            }

            model.addAttribute("transaction", transaction);
            model.addAttribute("categories", TransactionCategory.values());
            return "user/transaction-edit";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/user/transaction";
        }
    }

    @PostMapping("/update")
    public String updateTransaction(@ModelAttribute TransactionRecord transaction, HttpSession session, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        try {
            transactionService.updateTransaction(transaction.getId(), transaction, user);
            redirectAttributes.addFlashAttribute("successMsg", "Transaksi berhasil diperbarui!");
            return "redirect:/user/transaction";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            if (transaction.getId() != null) {
                return "redirect:/user/transaction/edit/" + transaction.getId();
            }
            return "redirect:/user/transaction";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/login";

        // FIX: Re-fetch user from DB
        User user = userService.findById(sessionUser.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }

        try {
            transactionService.deleteTransaction(id, user);
            redirectAttributes.addFlashAttribute("successMsg", "Transaksi berhasil dihapus!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/user/transaction";
    }
}