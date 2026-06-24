package com.financeall.service;

import com.financeall.model.User;
import com.financeall.model.Wallet;
import com.financeall.repository.UserRepository;
import com.financeall.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void register(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new RuntimeException("Username '" + user.getUsername() + "' sudah digunakan.");
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email '" + user.getEmail() + "' sudah terdaftar.");
        if (user.getPassword() == null || user.getPassword().length() < 8)
            throw new RuntimeException("Password minimal 8 karakter.");

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // SECURITY FIX: Hash recovery PIN just like a password — never store it plaintext.
        if (user.getRecoveryPin() != null && !user.getRecoveryPin().isEmpty()) {
            user.setRecoveryPin(passwordEncoder.encode(user.getRecoveryPin()));
        }
        userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        if (!passwordEncoder.matches(password, user.getPassword())) return null;
        if (user.isBanned()) {
            throw new RuntimeException("Akun Anda telah dinonaktifkan. Hubungi admin.");
        }
        return user;
    }

    @Transactional
    public void updateProfile(User sessionUser, User updatedData) {
        // FIX: Fetch fresh user from DB instead of using stale session object
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan!"));

        user.setFullName(updatedData.getFullName());
        user.setEmail(updatedData.getEmail());

        // HASH PASSWORD BARU - hanya jika diisi dan bukan hash lama
        if (updatedData.getPassword() != null
                && !updatedData.getPassword().isEmpty()
                && !updatedData.getPassword().startsWith("$2a$")) {
            user.setPassword(
                    passwordEncoder.encode(updatedData.getPassword())
            );
        }

        // SECURITY FIX: Hash the new recovery PIN before saving.
        if (updatedData.getRecoveryPin() != null
                && !updatedData.getRecoveryPin().isEmpty()
                && !updatedData.getRecoveryPin().startsWith("$2a$")) {
            user.setRecoveryPin(passwordEncoder.encode(updatedData.getRecoveryPin()));
        }

        userRepository.save(user);
    }

    /**
     * Update the admin profile from the admin profile form (username/email/password only).
     * Does NOT touch fullName/role/recoveryPin, and guards username uniqueness.
     */
    @Transactional
    public void updateAdminProfile(Long adminId, String username, String email, String password) {
        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin tidak ditemukan!"));

        if (username != null && !username.isBlank() && !username.equals(user.getUsername())) {
            User existing = userRepository.findByUsername(username.trim());
            if (existing != null && !existing.getId().equals(adminId)) {
                throw new RuntimeException("Username sudah digunakan!");
            }
            user.setUsername(username.trim());
        }

        if (email != null && !email.isBlank()) {
            user.setEmail(email.trim());
        }

        // Hash new password only if provided and not already a hash
        if (password != null && !password.isEmpty() && !password.startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String username,
                              String pin,
                              String newPassword) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Username tidak ditemukan!");
        }

        // SECURITY FIX: Compare hashed PIN using encoder, not plaintext equals.
        if (user.getRecoveryPin() == null ||
                !passwordEncoder.matches(pin, user.getRecoveryPin())) {
            throw new RuntimeException("PIN Pemulihan salah!");
        }

        // HASH PASSWORD BARU
        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(user);
    }

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