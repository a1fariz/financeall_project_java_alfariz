package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter // Gunakan Getter/Setter terpisah, jangan @Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String action;
    private String details;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; 

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}