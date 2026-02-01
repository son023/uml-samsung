package com.example.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorMenteeRegistrationDTO {
    private Long id;
    private Long mentorId;
    private String mentorName;
    private String mentorExpertise;
    private Long menteeId;
    private String menteeName;
    private String studentId;
    private String status;
    private String registeredAt;
    private String updatedAt;
    private String purpose;
    private String notes;
}
