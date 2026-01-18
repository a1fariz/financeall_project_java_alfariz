package com.financeall.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer requiredPoints;
}
