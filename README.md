# FinanceAll: Personal Finance Management System

**FinanceAll** adalah aplikasi manajemen keuangan pribadi berbasis Java Spring Boot yang dirancang dengan kesadaran bahwa mengelola uang bukan sekadar soal angka di atas kertas, melainkan soal perilaku dan psikologi manusia.

## 🧠 Filosofi & Latar Belakang
Proyek ini lahir dari inspirasi setelah membaca buku ***"The Psychology of Money"*** karya Morgan Housel. Dalam buku tersebut, Housel menekankan bahwa *"Doing well with money has a little to do with how smart you are and a lot to do with how you behave."*

Aplikasi ini mengimplementasikan beberapa pilar psikologi keuangan tersebut ke dalam fitur-fiturnya:

1.  **Mengelola Ketidakpastian (The Room for Error):** Housel mengajarkan pentingnya memiliki margin keamanan. Fitur **Emergency Fund Tracker** di aplikasi ini bukan sekadar tabungan, melainkan alat untuk memberi Anda kendali atas waktu dan ketenangan saat hal tak terduga terjadi.
2.  **Kekuatan Pengulangan (Compounding Behavior):** Kesuksesan finansial adalah hasil dari tindakan kecil yang disiplin. Melalui fitur **Gamified Leveling**, pengguna diberikan poin dan kenaikan level (seperti "Newbie") untuk menghargai konsistensi mereka dalam mencatat transaksi.
3.  **Kebebasan adalah Kekayaan Tertinggi:** Fokus pada pelunasan utang melalui **Debt Management** bertujuan agar pengguna bisa memiliki otoritas penuh atas hidup mereka tanpa beban finansial masa lalu.

## 🚀 Fitur Utama

* **Emergency Fund Tracker:** Menetapkan target dan memantau progres dana darurat secara real-time.
* **Gamified Experience:** Sistem level berbasis poin untuk mendorong kebiasaan mencatat keuangan yang berkelanjutan.
* **Debt Management:** Pencatatan dan monitoring pelunasan utang untuk menjaga kesehatan finansial.
* **Financial Health Dashboard:** Visualisasi statistik transaksi dan utang sistem secara menyeluruh.
* **Transaction Logic:** Pencatatan pemasukan dan pengeluaran yang terintegrasi dengan saldo dompet pengguna.

## 🛠️ Stack Teknologi

* **Backend:** Java 17 dengan Spring Boot 3.2.0.
* **Database:** MySQL dengan Spring Data JPA untuk persistensi data.
* **Frontend:** HTML5, Thymeleaf, dan Bootstrap untuk antarmuka yang responsif.
* **Security:** Spring Security 6 untuk perlindungan data pengguna.
* **Library:** Lombok (untuk kode yang lebih ringkas), Chart.js (untuk visualisasi), dan Jackson JSR310.

## ⚙️ Instalasi & Konfigurasi

### 1. Prasyarat
* Java JDK 17 atau lebih baru.
* Maven.
* MySQL Server.

### 2. Konfigurasi Database
Buat database bernama `financeall` di MySQL Anda, lalu sesuaikan kredensial pada file `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/financeall?useSSL=false&serverTimezone=Asia/Jakarta
spring.datasource.username=username_anda
spring.datasource.password=password_anda
spring.jpa.hibernate.ddl-auto=update