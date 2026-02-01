package com.example.study.repository;

import com.example.study.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>, JpaSpecificationExecutor<Mentor> {
    
    @Query("SELECT m FROM Mentor m WHERE m.active = true")
    List<Mentor> findAllActive();
    
    @Query("SELECT m FROM Mentor m WHERE m.active = true AND m.id NOT IN " +
           "(SELECT r.mentor.id FROM MentorMenteeRegistration r WHERE r.mentee.id = :menteeId)")
    List<Mentor> findAllActiveExcludingMentee(@Param("menteeId") Long menteeId);
}

