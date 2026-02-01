package com.example.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenteeDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String studentId;
    private String major;
    private Integer yearOfStudy;
    private Boolean active;
}
