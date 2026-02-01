package com.example.study.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenteeDTO extends UserDTO {
    @NotBlank(message = "Student ID is required")
    private String studentId;
    
    private String major;
    
    @Min(value = 1, message = "Year of study must be at least 1")
    private Integer yearOfStudy;
}

