package com.citronix.api.DTO;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HarvestDetailDto {

//    private Long id; // Optional, for cases where the DTO is used for updates or responses

    @Positive(message = "Quantity must be a positive value.")
    private double quantity;

    @NotNull(message = "Tree ID is required.")
    private Long treeId;

    @NotNull(message = "Harvest ID is required.")
    private Long harvestId;
}
