package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "wallets")
@Check(constraints = "balance >= 0 AND monthly_limit >= 0")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default // FIX
    private BigDecimal monthlyLimit = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude 
    private User user;
}