package com.citronix.api.DTO;

import com.citronix.api.domains.HarvestDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeDto {

    private Long id;

    @NotNull(message = "Tree date is required")
    @NotBlank(message = "Tree date is required")
    private LocalDate plantationDate;

    @NotNull(message = "Tree status is required")
    @NotBlank(message = "Tree status is required")
    private String status;
    private Long fieldId;

    @NotNull
    private Boolean isProductive;
}