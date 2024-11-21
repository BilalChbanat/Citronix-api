package com.citronix.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldDto {

//    private Long id;

    @NotBlank(message = "Field name is required")
    private String name;

    @Positive(message = "Area must be greater than zero")
    @NotNull(message = "Area cannot be null")
    private double area;

    @NotNull(message = "Farm ID is required")
    private Long farmId;

    private List<Long> treeIds;
}
