package com.citronix.api.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private double unitPrice;
    private double quantity;
    private String client;
    private double Revenue;

    @ManyToOne
    @JoinColumn(name = "harvest_id",nullable = false)
    private Harvest harvest;
}
