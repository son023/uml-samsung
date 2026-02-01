package com.example.study.controller;

import com.example.study.dto.MenteeDTO;
import com.example.study.service.MenteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentees")
@RequiredArgsConstructor
public class MenteeController {
    
    private final MenteeService menteeService;
    
    // Lấy danh sách tất cả mentees
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public ResponseEntity<List<MenteeDTO>> getAllMentees() {
        List<MenteeDTO> mentees = menteeService.getAllMentees();
        return ResponseEntity.ok(mentees);
    }
}
