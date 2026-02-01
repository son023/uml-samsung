package com.example.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String userType;
    
    public AuthResponse(String token, Long userId, String username, String userType) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.userType = userType;
    }
}

