package com.example.study.controller;

import com.example.study.dto.MentorDTO;
import com.example.study.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {
    
    private final MentorService mentorService;
    
    // Lấy danh sách mentors (chưa đăng ký nếu có menteeId)
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MentorDTO>> getAllMentors(
            @RequestParam(required = false) Long menteeId) {
        List<MentorDTO> mentors = mentorService.getAllMentors(menteeId);
        return ResponseEntity.ok(mentors);
    }
}
