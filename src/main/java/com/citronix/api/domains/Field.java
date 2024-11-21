package com.citronix.api.domains;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Field {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double area;

    @ManyToOne
    @JoinColumn(name = "farm_id",nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "field")
    private List<Tree> trees;

}
