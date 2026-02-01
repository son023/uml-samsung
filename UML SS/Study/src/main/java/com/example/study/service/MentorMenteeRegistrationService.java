package com.example.study.service;

import com.example.study.common.exception.DuplicateResourceException;
import com.example.study.common.exception.ResourceNotFoundException;
import com.example.study.dto.MentorMenteeRegistrationDTO;
import com.example.study.entity.Mentee;
import com.example.study.entity.Mentor;
import com.example.study.entity.MentorMenteeRegistration;
import com.example.study.mapper.MentorMenteeRegistrationMapper;
import com.example.study.repository.MenteeRepository;
import com.example.study.repository.MentorMenteeRegistrationRepository;
import com.example.study.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorMenteeRegistrationService {
    
    private final MentorMenteeRegistrationRepository registrationRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final MentorMenteeRegistrationMapper registrationMapper;
    
    @Transactional
    public MentorMenteeRegistrationDTO registerMentorMentee(MentorMenteeRegistrationDTO dto) {
        if (registrationRepository.existsByMentorIdAndMenteeId(dto.getMentorId(), dto.getMenteeId())) {
            throw new DuplicateResourceException("You have already registered with this mentor");
        }
        
        Mentor mentor = mentorRepository.findById(dto.getMentorId())
            .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", dto.getMentorId()));
        
        Mentee mentee = menteeRepository.findById(dto.getMenteeId())
            .orElseThrow(() -> new ResourceNotFoundException("Mentee", "id", dto.getMenteeId()));
        
        MentorMenteeRegistration registration = new MentorMenteeRegistration();
        registration.setMentor(mentor);
        registration.setMentee(mentee);
        registration.setStatus(MentorMenteeRegistration.RegistrationStatus.APPROVED);
        registration.setPurpose(dto.getPurpose());
        registration.setNotes(dto.getNotes());
        
        MentorMenteeRegistration saved = registrationRepository.save(registration);
        return registrationMapper.toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public MentorMenteeRegistrationDTO getRegistrationById(Long id) {
        MentorMenteeRegistration registration = registrationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Mentor-Mentee Registration", "id", id));
        return registrationMapper.toDTO(registration);
    }
    
    @Transactional(readOnly = true)
    public Page<MentorMenteeRegistrationDTO> getAllRegistrations(
            String search, 
            Long mentorId, 
            Long menteeId, 
            MentorMenteeRegistration.RegistrationStatus status,
            Pageable pageable) {
        
        Specification<MentorMenteeRegistration> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (mentorId != null) {
                predicates.add(cb.equal(root.get("mentor").get("id"), mentorId));
            }
            
            if (menteeId != null) {
                predicates.add(cb.equal(root.get("mentee").get("id"), menteeId));
            }
            
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";
                Join<MentorMenteeRegistration, Mentor> mentorJoin = root.join("mentor");
                Join<MentorMenteeRegistration, Mentee> menteeJoin = root.join("mentee");
                
                predicates.add(cb.or(
                    cb.like(cb.lower(mentorJoin.get("fullName")), searchPattern),
                    cb.like(cb.lower(mentorJoin.get("expertise")), searchPattern),
                    cb.like(cb.lower(menteeJoin.get("fullName")), searchPattern),
                    cb.like(cb.lower(menteeJoin.get("studentId")), searchPattern)
                ));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return registrationRepository.findAll(spec, pageable).map(registrationMapper::toDTO);
    }
    
    @Transactional
    public MentorMenteeRegistrationDTO updateRegistrationStatus(
            Long id, 
            MentorMenteeRegistration.RegistrationStatus status, 
            String notes) {
        MentorMenteeRegistration registration = registrationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Mentor-Mentee Registration", "id", id));
        
        registration.setStatus(status);
        if (notes != null) {
            registration.setNotes(notes);
        }
        
        MentorMenteeRegistration updated = registrationRepository.save(registration);
        return registrationMapper.toDTO(updated);
    }
    
    @Transactional
    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mentor-Mentee Registration", "id", id);
        }
        registrationRepository.deleteById(id);
    }
}

