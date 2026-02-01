package com.example.study.repository;

import com.example.study.entity.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long>, JpaSpecificationExecutor<Mentee> {
    Optional<Mentee> findByStudentId(String studentId);
    Boolean existsByStudentId(String studentId);
}

