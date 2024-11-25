package com.citronix.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HarvestDto {

//    private Long id;
    @NotNull(message = "Season is required")
    private String season;

    @NotNull(message = "Amount is required")
    private double amount;

    @NotNull(message = "Harvest date is required")
    private String harvestDate;

    private double totalQuantity;

}
