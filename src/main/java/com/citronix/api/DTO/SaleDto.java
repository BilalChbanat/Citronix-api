package com.citronix.api.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDto {

    private Long id;

    @NotNull(message = "Date cannot be null")
    private String date;

    @Positive(message = "Unit price must be a positive value")
    private double unitPrice;

    @Positive(message = "Quantity must be a positive value")
    private double quantity;

    @NotNull(message = "Client name cannot be blank")
    private String client;

    @PositiveOrZero(message = "Revenue must be zero or a positive value")
    private double revenue;

    @NotNull(message = "Harvest ID cannot be null")
    private Long harvestId;
}
