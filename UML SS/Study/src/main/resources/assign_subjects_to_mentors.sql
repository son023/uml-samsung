-- SQL script to assign subjects to mentors
-- This script assigns subjects based on mentor expertise

-- Assign Java-related subjects to mentor1 (John Smith - Java Programming)
-- Assign CS101 (Introduction to Java) to mentor1
INSERT INTO mentor_subjects (mentor_id, subject_id, assigned_at)
SELECT 
    m.id AS mentor_id,
    s.id AS subject_id,
    NOW() AS assigned_at
FROM users m
CROSS JOIN subjects s
WHERE m.username = 'mentor1'
  AND m.user_type = 'MENTOR'
  AND s.subject_code = 'CS101'
  AND NOT EXISTS (
    SELECT 1 FROM mentor_subjects ms 
    WHERE ms.mentor_id = m.id AND ms.subject_id = s.id
  );

-- Assign CS102 (Advanced Java) to mentor1
INSERT INTO mentor_subjects (mentor_id, subject_id, assigned_at)
SELECT 
    m.id AS mentor_id,
    s.id AS subject_id,
    NOW() AS assigned_at
FROM users m
CROSS JOIN subjects s
WHERE m.username = 'mentor1'
  AND m.user_type = 'MENTOR'
  AND s.subject_code = 'CS102'
  AND NOT EXISTS (
    SELECT 1 FROM mentor_subjects ms 
    WHERE ms.mentor_id = m.id AND ms.subject_id = s.id
  );

-- Assign Web Development subjects to mentor2 (Jane Doe - Web Development)
-- Assign WEB201 (Web Development) to mentor2
INSERT INTO mentor_subjects (mentor_id, subject_id, assigned_at)
SELECT 
    m.id AS mentor_id,
    s.id AS subject_id,
    NOW() AS assigned_at
FROM users m
CROSS JOIN subjects s
WHERE m.username = 'mentor2'
  AND m.user_type = 'MENTOR'
  AND s.subject_code = 'WEB201'
  AND NOT EXISTS (
    SELECT 1 FROM mentor_subjects ms 
    WHERE ms.mentor_id = m.id AND ms.subject_id = s.id
  );

-- Assign CS102 (Advanced Java) to mentor2 (since web development uses Java/Spring Boot)
INSERT INTO mentor_subjects (mentor_id, subject_id, assigned_at)
SELECT 
    m.id AS mentor_id,
    s.id AS subject_id,
    NOW() AS assigned_at
FROM users m
CROSS JOIN subjects s
WHERE m.username = 'mentor2'
  AND m.user_type = 'MENTOR'
  AND s.subject_code = 'CS102'
  AND NOT EXISTS (
    SELECT 1 FROM mentor_subjects ms 
    WHERE ms.mentor_id = m.id AND ms.subject_id = s.id
  );

-- Verify the assignments
SELECT 
    ms.id,
    m.username AS mentor_username,
    m.full_name AS mentor_name,
    s.subject_code,
    s.subject_name,
    ms.assigned_at
FROM mentor_subjects ms
JOIN users m ON ms.mentor_id = m.id
JOIN subjects s ON ms.subject_id = s.id
ORDER BY m.username, s.subject_code;
