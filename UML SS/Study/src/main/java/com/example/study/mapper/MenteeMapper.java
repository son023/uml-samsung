package com.example.study.mapper;

import com.example.study.dto.MenteeDTO;
import com.example.study.entity.Mentee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MenteeMapper {
    MenteeDTO toDTO(Mentee mentee);
    Mentee toEntity(MenteeDTO dto);
    void updateEntityFromDTO(MenteeDTO dto, @MappingTarget Mentee mentee);
}

