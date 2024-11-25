package com.citronix.api.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @NotNull(message = "Unit Price is required")
    private double unitPrice;

    @NotNull(message = "Quantity is required")
    private double quantity;

    private String client;
    private double Revenue;

    @ManyToOne
    @JoinColumn(name = "harvest_id",nullable = false)
    private Harvest harvest;
}
