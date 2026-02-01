package com.example.study.mapper;

import com.example.study.dto.AdminDTO;
import com.example.study.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdminMapper {
    AdminDTO toDTO(Admin admin);
    Admin toEntity(AdminDTO dto);
    void updateEntityFromDTO(AdminDTO dto, @MappingTarget Admin admin);
}

