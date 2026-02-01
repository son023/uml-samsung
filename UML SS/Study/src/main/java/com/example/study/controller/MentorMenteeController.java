package com.example.study.controller;

import com.example.study.dto.MentorMenteeRegistrationDTO;
import com.example.study.entity.MentorMenteeRegistration;
import com.example.study.service.MentorMenteeRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class MentorMenteeController {
    
    private final MentorMenteeRegistrationService registrationService;
    
    // Mentee đăng ký với mentor
    @PostMapping
    @PreAuthorize("hasRole('MENTEE')")
    public ResponseEntity<MentorMenteeRegistrationDTO> registerWithMentor(
            @Valid @RequestBody MentorMenteeRegistrationDTO dto) {
        MentorMenteeRegistrationDTO created = registrationService.registerMentorMentee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    // Xem tất cả đăng ký (All users có thể xem)
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MentorMenteeRegistrationDTO>> getAllRegistrations(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long mentorId,
            @RequestParam(required = false) Long menteeId,
            @RequestParam(required = false) MentorMenteeRegistration.RegistrationStatus status,
            @PageableDefault(size = 10, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MentorMenteeRegistrationDTO> registrations = registrationService.getAllRegistrations(
                search, mentorId, menteeId, status, pageable);
        return ResponseEntity.ok(registrations);
    }
    
    // Xem chi tiết đăng ký
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MentorMenteeRegistrationDTO> getRegistrationById(@PathVariable Long id) {
        MentorMenteeRegistrationDTO registration = registrationService.getRegistrationById(id);
        return ResponseEntity.ok(registration);
    }
    
    // Mentee hủy đăng ký
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MENTEE')")
    public ResponseEntity<Void> cancelRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
    
    // Mentor approve/reject đăng ký
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<MentorMenteeRegistrationDTO> updateRegistrationStatus(
            @PathVariable Long id,
            @RequestParam MentorMenteeRegistration.RegistrationStatus status,
            @RequestParam(required = false) String notes) {
        MentorMenteeRegistrationDTO updated = registrationService.updateRegistrationStatus(id, status, notes);
        return ResponseEntity.ok(updated);
    }
}
