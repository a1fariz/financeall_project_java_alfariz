package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String walletName; 

    @Column(columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal balance;

    @Column(columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal monthlyLimit; 

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude 
    private User user;
}