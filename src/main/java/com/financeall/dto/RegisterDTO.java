package com.financeall.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotEmpty(message = "Username tidak boleh kosong")
    private String username;

    @NotEmpty(message = "Password tidak boleh kosong")
    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

    @NotEmpty(message = "Nama lengkap harus diisi")
    private String fullName;

    // --- TAMBAHAN BARU ---
    @NotEmpty(message = "PIN Pemulihan wajib diisi")
    @Size(min = 6, max = 6, message = "PIN harus tepat 6 digit angka")
    @Pattern(regexp = "\\d{6}", message = "PIN hanya boleh berisi angka")
    private String recoveryPin;
}