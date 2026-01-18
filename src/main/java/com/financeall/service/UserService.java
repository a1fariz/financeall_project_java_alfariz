package com.financeall.service;

import com.financeall.model.User;
import com.financeall.model.Wallet;
import com.financeall.repository.UserRepository;
import com.financeall.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void register(User user) {
        user.setRole("USER");
        userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Transactional
    public void updateProfile(User user, User updatedData) {
        user.setFullName(updatedData.getFullName());
        user.setEmail(updatedData.getEmail());
        if (updatedData.getPassword() != null && !updatedData.getPassword().isEmpty()) {
            user.setPassword(updatedData.getPassword());
        }
        if (updatedData.getRecoveryPin() != null && !updatedData.getRecoveryPin().isEmpty()) {
            user.setRecoveryPin(updatedData.getRecoveryPin());
        }
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String username, String pin, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("Username tidak ditemukan!");
        if (user.getRecoveryPin() == null || !user.getRecoveryPin().equals(pin)) {
            throw new RuntimeException("PIN Pemulihan salah!");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

 // Pastikan wallet selalu terinisialisasi dengan balance 0
@Transactional
public Wallet getOrCreateWallet(User user) {
    return walletRepository.findByUser(user).orElseGet(() -> {
        Wallet newWallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .monthlyLimit(BigDecimal.ZERO)
                .build();
        return walletRepository.save(newWallet);
    });
}
}