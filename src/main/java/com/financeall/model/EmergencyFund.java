package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "emergency_funds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyFund implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal currentAmount; // Harus match dengan service
    private BigDecimal targetAmount;
}