package com.example.study.mapper;

import com.example.study.dto.SubjectDTO;
import com.example.study.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "subjectCode", source = "subjectCode")
    @Mapping(target = "subjectName", source = "subjectName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "credits", source = "credits")
    SubjectDTO toDTO(Subject subject);
}
