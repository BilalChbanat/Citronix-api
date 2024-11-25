package com.citronix.api.domains;

import com.citronix.api.enums.TreeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Tree {
    public static final double YOUNG_TREE_PRODUCTIVITY = 2.5;  // kg per season
    public static final double MATURE_TREE_PRODUCTIVITY = 12.0; // kg per season
    public static final double OLD_TREE_PRODUCTIVITY = 20.0;    // kg per season
    public static final int YOUNG_TREE_AGE_LIMIT = 3;     // years
    public static final int MATURE_TREE_AGE_LIMIT = 10;   // years
    public static final int MAX_TREE_AGE = 20;            // years
    public static final int PLANTING_START_MONTH = 3;     // March
    public static final int PLANTING_END_MONTH = 5;       // May


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate plantationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TreeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id",nullable = false)
    private Field field;


    @OneToMany(mappedBy = "tree")
    private List<HarvestDetail> harvestDetails;


    public int getAge() {
        return LocalDate.now().getYear() - plantationDate.getYear();
    }

    public double getProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12.0;
        } else if (age <= 20) {
            return 20.0;
        } else {
            return 0.0;
        }
    }

    public boolean isProductiveAge() {
        int age = getAge();
        return age >= 3 && age <= 20; // Productive age is between 3 and 20 years
    }

}
