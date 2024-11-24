package com.citronix.api.service.interfaces;

import com.citronix.api.DTO.FieldDto;
import com.citronix.api.DTO.TreeDto;
import com.citronix.api.domains.Field;
import com.citronix.api.domains.Tree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TreeService {

    TreeDto create(TreeDto treeDto);

    void delete(Long id);

    Tree findById(Long id);

    TreeDto update(Long id, TreeDto treeDto);

    Page<TreeDto> findAll(Pageable pageable);
}
