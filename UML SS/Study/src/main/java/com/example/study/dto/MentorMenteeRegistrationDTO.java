package com.example.study.dto;

import com.example.study.entity.MentorMenteeRegistration;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorMenteeRegistrationDTO {
    private Long id;
    
    @NotNull(message = "Mentor ID is required")
    private Long mentorId;
    
    private String mentorName;
    private String mentorExpertise;
    
    @NotNull(message = "Mentee ID is required")
    private Long menteeId;
    
    private String menteeName;
    private String studentId;
    private MentorMenteeRegistration.RegistrationStatus status;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private String purpose;
    private String notes;
}

