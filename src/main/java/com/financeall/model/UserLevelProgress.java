package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "user_level_progress")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserLevelProgress implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "level_id") // Sesuai DB
    private Level level; // Diubah agar menghasilkan getLevel()

    @Column(nullable = false)
    private Integer currentPoints; 
}