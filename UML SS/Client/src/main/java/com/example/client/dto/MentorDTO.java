package com.example.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String expertise;
    private Integer yearsOfExperience;
    private String bio;
    private Boolean active;
    private List<SubjectDTO> subjects;
}
