# 💰 FinanceAll — Integrated Personal Finance Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot 3.2"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL 16"/>
  <img src="https://img.shields.io/badge/Flyway-Migration-CC0200?style=for-the-badge&logo=flyway&logoColor=white" alt="Flyway"/>
  <img src="https://img.shields.io/badge/Thymeleaf-3.1-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf"/>
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Compose"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="MIT License"/>
</p>

**FinanceAll** adalah platform manajemen keuangan pribadi berbasis web tingkat produksi yang dibangun menggunakan **Java Spring Boot**. Terinspirasi dari buku *"The Psychology of Money"* oleh Morgan Housel, aplikasi ini memandang pengelolaan finansial bukan sekadar rumus matematika, melainkan sebuah **tantangan perilaku (behavioral habits)** melalui integrasi pelacak dana darurat, pelunasan hutang terstruktur, kalkulator Financial Independence (FI), dan sistem gamifikasi kenaikan level.

---

## 📑 Daftar Isi
- [Fitur Utama](#-fitur-utama)
- [Galeri Tangkapan Layar](#-galeri-tangkapan-layar)
- [Arsitektur Sistem](#-arsitektur-sistem)
- [Fitur Keamanan & Proteksi Data](#-fitur-keamanan--proteksi-data)
- [Teknologi yang Digunakan](#-teknologi-yang-digunakan)
- [Panduan Menjalankan Aplikasi](#-panduan-menjalankan-aplikasi)
- [Struktur Proyek](#-struktur-proyek)
- [Author & Lisensi](#-author--lisensi)

---

## ✨ Fitur Utama

| Modul | Deskripsi |
|---|---|
| 📊 **Dashboard Finansial** | Ringkasan saldo total, visualisasi cashflow bulanan, dan log aktivitas transaksi terbaru. |
| 💳 **Pencatatan Transaksi** | Catat pemasukan dan pengeluaran secara terperinci berdasarkan kategori dan tanggal. |
| 👛 **Manajemen Dompet** | Pantau likuiditas saldo kas, rekening bank, e-wallet, dan arus kas per dompet. |
| 📉 **Manajemen Hutang** | Rencanakan dan pantau pelunasan kewajiban/hutang dengan status pembayaran terstruktur. |
| 🛡️ **Dana Darurat (Emergency Fund)** | Bangun jaring pengaman finansial dengan target dinamis dan indikator progres visual. |
| 🎯 **Kalkulator FI (Financial Independence)** | Hitung target kebebasan finansial berdasarkan aturan 4% (*The 4% Rule*) dan rasio tabungan. |
| 🏆 **Leveling & Gamifikasi** | Sistem level dan poin yang memotivasi konsistensi pencatatan keuangan harian. |
| 🔑 **PIN Pemulihan Akun (Recovery PIN)** | Reset kata sandi secara instan dan aman tanpa bergantung pada layanan email. |
| 🛡️ **Panel Administrator** | Manajemen pengguna, pemblokiran akun, publikasi artikel edukasi, dan log audit aktivitas. |

---

## 📸 Galeri Tangkapan Layar

### 🔐 Autentikasi & Akun
<table>
  <tr>
    <td align="center"><strong>Halaman Login</strong></td>
    <td align="center"><strong>Halaman Registrasi</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/login.png" width="400" alt="Login"/></td>
    <td><img src="docs/screenshots/register.png" width="400" alt="Register"/></td>
  </tr>
</table>

### 👤 Panel Pengguna
<table>
  <tr>
    <td align="center"><strong>Dashboard Pengguna</strong></td>
    <td align="center"><strong>Riwayat Transaksi</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/user-dashboard.png" width="400" alt="Dashboard"/></td>
    <td><img src="docs/screenshots/user-transactions.png" width="400" alt="Transactions"/></td>
  </tr>
  <tr>
    <td align="center"><strong>Manajemen Dompet</strong></td>
    <td align="center"><strong>Pelunasan Hutang</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/user-wallet.png" width="400" alt="Wallet"/></td>
    <td><img src="docs/screenshots/user-debt.png" width="400" alt="Debt"/></td>
  </tr>
  <tr>
    <td align="center"><strong>Dana Darurat</strong></td>
    <td align="center"><strong>Kalkulator FI</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/user-emergency.png" width="400" alt="Emergency Fund"/></td>
    <td><img src="docs/screenshots/user-fi.png" width="400" alt="FI Calculator"/></td>
  </tr>
  <tr>
    <td align="center"><strong>Gamifikasi & Level</strong></td>
    <td align="center"><strong>Profil Pengguna</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/user-level.png" width="400" alt="Level"/></td>
    <td><img src="docs/screenshots/user-profile.png" width="400" alt="Profile"/></td>
  </tr>
</table>

### 🛡️ Panel Administrator
<table>
  <tr>
    <td align="center"><strong>Admin Dashboard</strong></td>
    <td align="center"><strong>Manajemen Pengguna</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/admin-dashboard.png" width="400" alt="Admin Dashboard"/></td>
    <td><img src="docs/screenshots/admin-users.png" width="400" alt="Admin Users"/></td>
  </tr>
  <tr>
    <td align="center"><strong>Artikel Finansial</strong></td>
    <td align="center"><strong>Log Audit Aktivitas</strong></td>
  </tr>
  <tr>
    <td><img src="docs/screenshots/admin-articles.png" width="400" alt="Admin Articles"/></td>
    <td><img src="docs/screenshots/admin-logs.png" width="400" alt="Admin Logs"/></td>
  </tr>
</table>

---

## 🏗️ Arsitektur Sistem

```
┌────────────────────────────────────────────────────────┐
│                   Browser (Client)                     │
│           HTML5 + Thymeleaf + Bootstrap 5              │
└───────────────────────────┬────────────────────────────┘
                            │ HTTP Requests
                            ▼
┌────────────────────────────────────────────────────────┐
│               Spring Boot MVC Controllers              │
│       - AuthController        - AdminController        │
│       - TransactionController - GoalController         │
└───────────────────────────┬────────────────────────────┘
                            │ Service Invocations
                            ▼
┌────────────────────────────────────────────────────────┐
│                 Business Logic Layer                   │
│       - UserService           - TransactionService     │
│       - GamificationService   - ReportService          │
└───────────────────────────┬────────────────────────────┘
                            │ Spring Data JPA
                            ▼
┌────────────────────────────────────────────────────────┐
│                PostgreSQL 16 Database                  │
│       (Skema & Migrasi dikelola otomatis Flyway)       │
└────────────────────────────────────────────────────────┘
```

---

## 🛡️ Fitur Keamanan & Proteksi Data

- **BCrypt Password Hashing**: Seluruh kata sandi pengguna dienkripsi dengan *salt* sebelum disimpan.
- **Session-Based Authentication & Interceptor**: Proteksi ketat rute `/user/**` dan `/admin/**`.
- **Pencegahan IDOR (Insecure Direct Object Reference)**: Verifikasi kepemilikan data pada setiap operasi modifikasi dan penghapusan transaksi/dompet.
- **6-Digit Recovery PIN**: Solusi pemulihan akses akun mandiri tanpa ketergantungan API pihak ketiga.
- **Role-Based Access Control**: Pemisahan hak akses mutlak antara pengguna biasa dan administrator.

---

## 🚀 Panduan Menjalankan Aplikasi

### Opsi A: Menjalankan dengan Docker Compose (Direkomendasikan)

1. **Clone Repositori**:
   ```bash
   git clone https://github.com/a1fariz/financeall_project_java_alfariz.git
   cd financeall_project_java_alfariz
   ```
2. **Siapkan File Lingkungan**:
   ```bash
   cp .env.example .env
   ```
3. **Jalankan Aplikasi & PostgreSQL**:
   ```bash
   docker compose up -d --build
   ```
4. Buka browser pada `http://localhost:8080`.

### Opsi B: Menjalankan Secara Lokal (Maven)

Prasyarat: **Java 17+** dan **PostgreSQL** sudah terpasang.

```bash
# 1. Buat database
createdb financeall

# 2. Jalankan aplikasi via Maven Wrapper
./mvnw spring-boot:run
# Windows: .\mvnw.cmd spring-boot:run
```

---

## 📁 Struktur Proyek

```text
financeall/
├── src/main/java/com/financeall/
│   ├── config/              # Konfigurasi Security, Web, & Data Seeder
│   ├── controller/          # Spring MVC & REST Controllers
│   ├── dto/                 # Data Transfer Objects
│   ├── model/               # Entitas JPA Hibernate
│   ├── repository/          # Spring Data JPA Repositories
│   └── service/             # Layer Logika Bisnis
├── src/main/resources/
│   ├── db/migration/        # Skrip Migrasi SQL Flyway
│   ├── static/              # Asset CSS, JS, & Gambar
│   ├── templates/           # Template UI Thymeleaf
│   └── application.properties
├── docker-compose.yml       # Orkestrasi Docker
├── pom.xml                  # Konfigurasi Dependensi Maven
└── README.md
```

---

## 👨‍💻 Author

Dikembangkan oleh **Muhammad Hafiz Alfarizi** ([@a1fariz](https://github.com/a1fariz)).  
*Dibuat untuk menghadirkan kebebasan finansial melalui kebiasaan keuangan yang terukur.*
