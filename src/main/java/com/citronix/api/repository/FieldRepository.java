package com.citronix.api.repository;

import com.citronix.api.domains.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query("SELECT COALESCE(SUM(f.area), 0) FROM Field f WHERE f.farm.id = :farmId")
    double findTotalAreaByFarmId(@Param("farmId") Long farmId);

    Boolean existsByName(String name);
}
