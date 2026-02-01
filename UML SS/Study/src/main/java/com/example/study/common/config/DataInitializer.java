package com.example.study.common.config;

import com.example.study.entity.*;
import com.example.study.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    private final PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initData(
            AdminRepository adminRepository,
            MentorRepository mentorRepository,
            MenteeRepository menteeRepository,
            SubjectRepository subjectRepository) {
        
        return args -> {
            // Check if data already exists
            if (adminRepository.count() > 0) {
                return;
            }
            
            // Create Admin
            Admin admin = Admin.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@study.com")
                    .fullName("System Administrator")
                    .phone("0123456789")
                    .userType(User.UserType.ADMIN)
                    .active(true)
                    .department("IT")
                    .position("System Admin")
                    .build();
            adminRepository.save(admin);
            
            // Create Mentors
            Mentor mentor1 = Mentor.builder()
                    .username("mentor1")
                    .password(passwordEncoder.encode("mentor123"))
                    .email("mentor1@study.com")
                    .fullName("John Smith")
                    .phone("0987654321")
                    .userType(User.UserType.MENTOR)
                    .active(true)
                    .expertise("Java Programming")
                    .yearsOfExperience(5)
                    .bio("Experienced Java developer with 5 years in software development")
                    .build();
            mentorRepository.save(mentor1);
            
            Mentor mentor2 = Mentor.builder()
                    .username("mentor2")
                    .password(passwordEncoder.encode("mentor123"))
                    .email("mentor2@study.com")
                    .fullName("Jane Doe")
                    .phone("0912345678")
                    .userType(User.UserType.MENTOR)
                    .active(true)
                    .expertise("Web Development")
                    .yearsOfExperience(7)
                    .bio("Full-stack web developer specializing in React and Spring Boot")
                    .build();
            mentorRepository.save(mentor2);
            
            // Create Mentees
            Mentee mentee1 = Mentee.builder()
                    .username("mentee1")
                    .password(passwordEncoder.encode("mentee123"))
                    .email("mentee1@study.com")
                    .fullName("Alice Johnson")
                    .phone("0934567890")
                    .userType(User.UserType.MENTEE)
                    .active(true)
                    .studentId("ST001")
                    .major("Computer Science")
                    .yearOfStudy(2)
                    .build();
            menteeRepository.save(mentee1);
            
            Mentee mentee2 = Mentee.builder()
                    .username("mentee2")
                    .password(passwordEncoder.encode("mentee123"))
                    .email("mentee2@study.com")
                    .fullName("Bob Williams")
                    .phone("0945678901")
                    .userType(User.UserType.MENTEE)
                    .active(true)
                    .studentId("ST002")
                    .major("Software Engineering")
                    .yearOfStudy(3)
                    .build();
            menteeRepository.save(mentee2);
            
            // Create Subjects
            Subject subject1 = new Subject();
            subject1.setSubjectCode("CS101");
            subject1.setSubjectName("Introduction to Java");
            subject1.setDescription("Learn the basics of Java programming");
            subject1.setCredits(3);
            subject1.setMaxStudents(30);
            subject1.setActive(true);
            subjectRepository.save(subject1);
            
            Subject subject2 = new Subject();
            subject2.setSubjectCode("CS102");
            subject2.setSubjectName("Advanced Java");
            subject2.setDescription("Advanced concepts in Java development");
            subject2.setCredits(4);
            subject2.setMaxStudents(25);
            subject2.setActive(true);
            subjectRepository.save(subject2);
            
            Subject subject3 = new Subject();
            subject3.setSubjectCode("WEB201");
            subject3.setSubjectName("Web Development");
            subject3.setDescription("Full-stack web development with React and Spring Boot");
            subject3.setCredits(4);
            subject3.setMaxStudents(20);
            subject3.setActive(true);
            subjectRepository.save(subject3);
            
            System.out.println("========================================");
            System.out.println("Sample data initialized successfully!");
            System.out.println("========================================");
            System.out.println("Admin - username: admin, password: admin123");
            System.out.println("Mentor1 - username: mentor1, password: mentor123");
            System.out.println("Mentor2 - username: mentor2, password: mentor123");
            System.out.println("Mentee1 - username: mentee1, password: mentee123");
            System.out.println("Mentee2 - username: mentee2, password: mentee123");
            System.out.println("========================================");
        };
    }
}

