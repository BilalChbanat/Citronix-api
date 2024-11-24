package com.citronix.api.mapper;

import com.citronix.api.DTO.TreeDto;
import com.citronix.api.domains.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(target = "harvestDetails", ignore = true)
    @Mapping(target = "field", ignore = true)
    Tree toTree(TreeDto treeDto);

    @Mapping(target = "status", expression = "java(tree.getStatus() != null ? tree.getStatus().name() : null)")
    TreeDto toDto(Tree tree);
}