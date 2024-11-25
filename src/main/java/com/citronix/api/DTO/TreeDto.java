package com.citronix.api.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeDto {

//    private Long id;
    private LocalDateTime plantationDate;
    private String status;
    private Long fieldId;
    private Long harvestDetails;
}
