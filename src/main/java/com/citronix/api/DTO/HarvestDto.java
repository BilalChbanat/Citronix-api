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
    @NotBlank(message = "Season cannot be blank")
    @NotNull(message = "Season is required")
    private String season;

    @NotBlank(message = "Amount cannot be blank")
    @NotNull(message = "Amount is required")
    private double amount;

    @NotBlank(message = "Harvest date cannot be blank")
    @NotNull(message = "Harvest date is required")
    private String harvestDate;


    private double totalQuantity;

}
