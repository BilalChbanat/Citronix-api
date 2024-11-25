package com.citronix.api.repository;

import com.citronix.api.domains.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    long countByFieldId(Long fieldId);

    List<Tree> findByFieldId(@Param("fieldId") Long fieldId);



}
