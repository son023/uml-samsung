package com.example.study.repository;

import com.example.study.entity.MentorSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorSubjectRepository extends JpaRepository<MentorSubject, Long>, JpaSpecificationExecutor<MentorSubject> {
    Optional<MentorSubject> findByMentorIdAndSubjectId(Long mentorId, Long subjectId);
    Boolean existsByMentorIdAndSubjectId(Long mentorId, Long subjectId);
    
    List<MentorSubject> findByMentorId(Long mentorId);
    List<MentorSubject> findBySubjectId(Long subjectId);
    
    @Query("SELECT ms FROM MentorSubject ms JOIN FETCH ms.subject WHERE ms.mentor.id = :mentorId")
    List<MentorSubject> findByMentorIdWithSubject(@Param("mentorId") Long mentorId);
    
    @Query("SELECT ms FROM MentorSubject ms JOIN FETCH ms.mentor WHERE ms.subject.id = :subjectId")
    List<MentorSubject> findBySubjectIdWithMentor(@Param("subjectId") Long subjectId);
}
