package com.example.study.mapper;

import com.example.study.dto.MentorDTO;
import com.example.study.entity.Mentor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MentorMapper {
    MentorDTO toDTO(Mentor mentor);
    Mentor toEntity(MentorDTO dto);
    void updateEntityFromDTO(MentorDTO dto, @MappingTarget Mentor mentor);
}

