package com.example.study.repository;

import com.example.study.entity.MentorMenteeRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorMenteeRegistrationRepository extends JpaRepository<MentorMenteeRegistration, Long>, JpaSpecificationExecutor<MentorMenteeRegistration> {
    Optional<MentorMenteeRegistration> findByMentorIdAndMenteeId(Long mentorId, Long menteeId);
    Boolean existsByMentorIdAndMenteeId(Long mentorId, Long menteeId);
    
    List<MentorMenteeRegistration> findByMentorId(Long mentorId);
    List<MentorMenteeRegistration> findByMenteeId(Long menteeId);
    
    @Query("SELECT r FROM MentorMenteeRegistration r JOIN FETCH r.mentee WHERE r.mentor.id = :mentorId")
    List<MentorMenteeRegistration> findByMentorIdWithMentee(@Param("mentorId") Long mentorId);
    
    @Query("SELECT r FROM MentorMenteeRegistration r JOIN FETCH r.mentor WHERE r.mentee.id = :menteeId")
    List<MentorMenteeRegistration> findByMenteeIdWithMentor(@Param("menteeId") Long menteeId);
}

