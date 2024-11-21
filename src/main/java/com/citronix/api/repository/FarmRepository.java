package com.citronix.api.repository;

import com.citronix.api.domains.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FarmRepository extends JpaRepository<Farm, Long> {

    Boolean existsByName(String name);
}