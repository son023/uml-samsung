package com.example.study.service;

import com.example.study.dto.MenteeDTO;
import com.example.study.entity.Mentee;
import com.example.study.mapper.MenteeMapper;
import com.example.study.repository.MenteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenteeService {
    
    private final MenteeRepository menteeRepository;
    private final MenteeMapper menteeMapper;
    
    @Transactional(readOnly = true)
    public List<MenteeDTO> getAllMentees() {
        List<Mentee> mentees = menteeRepository.findAll();
        return mentees.stream()
                .filter(mentee -> mentee.getActive() != null && mentee.getActive())
                .map(menteeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
