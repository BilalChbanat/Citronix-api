package com.citronix.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmDto {



    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "location is required")
    private String location;

    @Positive(message = "Area must be greater than zero")
    @NotNull(message = "Area cannot be null")
    private double area;

    private LocalDateTime createdAt = LocalDateTime.now();
}
