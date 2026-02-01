package com.example.study.service;

import com.example.study.dto.MentorDTO;
import com.example.study.dto.SubjectDTO;
import com.example.study.entity.Mentor;
import com.example.study.entity.MentorSubject;
import com.example.study.mapper.MentorMapper;
import com.example.study.mapper.SubjectMapper;
import com.example.study.repository.MentorRepository;
import com.example.study.repository.MentorSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorService {
    
    private final MentorRepository mentorRepository;
    private final MentorSubjectRepository mentorSubjectRepository;
    private final MentorMapper mentorMapper;
    private final SubjectMapper subjectMapper;
    
    @Transactional(readOnly = true)
    public List<MentorDTO> getAllMentors(Long menteeId) {
        // Fetch mentors
        List<Mentor> mentors;
        if (menteeId != null) {
            // Fetch mentors that mentee hasn't registered with yet
            mentors = mentorRepository.findAllActiveExcludingMentee(menteeId);
        } else {
            // Fetch all active mentors
            mentors = mentorRepository.findAllActive();
        }
        
        // Map to DTOs - map mentor first, then fetch and map subjects separately
        return mentors.stream()
                .map(mentor -> {
                    MentorDTO dto = mentorMapper.toDTO(mentor);
                    // Fetch subjects through MentorSubjectRepository
                    List<MentorSubject> mentorSubjects = mentorSubjectRepository.findByMentorIdWithSubject(mentor.getId());
                    if (!mentorSubjects.isEmpty()) {
                        List<SubjectDTO> subjects = mentorSubjects.stream()
                                .map(MentorSubject::getSubject)
                                .map(subjectMapper::toDTO)
                                .collect(Collectors.toList());
                        dto.setSubjects(subjects);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
