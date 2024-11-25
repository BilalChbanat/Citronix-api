package com.citronix.api.domains;


import com.citronix.api.enums.SeasonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private SeasonType season;

    @NotNull(message = "Amount is required")
    private double amount;

    @NotNull(message = "Date is required")
    private LocalDate harvestDate;

    @NotNull(message = "Total Quantity is required")
    private double totalQuantity;

    @OneToMany(mappedBy = "harvest")
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest")
    private List<Sale> sales;
}
