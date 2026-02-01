package com.example.study.mapper;

import com.example.study.dto.MentorMenteeRegistrationDTO;
import com.example.study.entity.MentorMenteeRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MentorMenteeRegistrationMapper {
    
    @Mapping(source = "mentor.id", target = "mentorId")
    @Mapping(source = "mentor.fullName", target = "mentorName")
    @Mapping(source = "mentor.expertise", target = "mentorExpertise")
    @Mapping(source = "mentee.id", target = "menteeId")
    @Mapping(source = "mentee.fullName", target = "menteeName")
    @Mapping(source = "mentee.studentId", target = "studentId")
    MentorMenteeRegistrationDTO toDTO(MentorMenteeRegistration registration);
}

