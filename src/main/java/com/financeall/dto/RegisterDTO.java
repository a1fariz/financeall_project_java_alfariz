package com.financeall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message = "Nama lengkap wajib diisi")
    @Size(min = 3, max = 100,
            message = "Nama minimal 3 karakter")
    private String fullName;

    @NotBlank(message = "Username wajib diisi")
    @Size(min = 4, max = 30,
            message = "Username minimal 4 karakter")
    private String username;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password wajib diisi")
    @Size(min = 8,
            message = "Password minimal 8 karakter")
    private String password;

    @NotBlank(message = "Konfirmasi password wajib diisi")
    private String confirmPassword;

    @NotBlank(message = "Recovery PIN wajib diisi")
    @Pattern(regexp = "\\d{6}",
            message = "Recovery PIN harus 6 digit angka")
    private String recoveryPin;
}