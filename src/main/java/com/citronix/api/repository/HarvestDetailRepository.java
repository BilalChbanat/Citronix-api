package com.citronix.api.repository;

import com.citronix.api.domains.Harvest;
import com.citronix.api.domains.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {

}
