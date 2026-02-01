package com.example.study.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO extends UserDTO {
    private String expertise;
    
    @Min(value = 0, message = "Years of experience must be positive")
    private Integer yearsOfExperience;
    
    private String bio;
    
    private List<SubjectDTO> subjects;
}

