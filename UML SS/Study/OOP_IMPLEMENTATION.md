# OOP Principles Implementation - Study Management System

## Tổng quan

Hệ thống Study Management được xây dựng dựa trên các nguyên lý OOP (Object-Oriented Programming) cơ bản. Dưới đây là chi tiết cách áp dụng 4 nguyên lý chính của OOP.

---

## 1. Kế thừa (Inheritance) ⭐⭐⭐

### Implementation

#### Abstract Class `User`
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private UserType userType;
    // ... common fields
}
```

#### Concrete Classes
```java
// Admin extends User
@Entity
public class Admin extends User {
    private String department;
    private String position;
}

// Mentor extends User
@Entity
public class Mentor extends User {
    private String expertise;
    private Integer yearsOfExperience;
    private String bio;
}

// Mentee extends User
@Entity
public class Mentee extends User {
    private String studentId;
    private String major;
    private Integer yearOfStudy;
}
```

### Benefits
- **Code reuse:** Các thuộc tính chung (username, email, phone) chỉ định nghĩa 1 lần
- **Easy maintenance:** Thay đổi User class sẽ áp dụng cho tất cả subclasses
- **Type hierarchy:** Có thể xử lý tất cả users thông qua User reference

### JPA Strategy
- Sử dụng `InheritanceType.JOINED`
- Tạo bảng riêng cho mỗi entity
- Join tables khi query

---

## 2. Đóng gói (Encapsulation) ⭐⭐⭐

### Implementation

#### Entity Level
```java
@Data  // Lombok generates getters/setters
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private Long id;           // Private fields
    private String subjectCode;
    private String subjectName;
    // ... other private fields
}
```

#### Service Layer
```java
@Service
@RequiredArgsConstructor  // Dependency injection through constructor
public class MenteeService {
    private final MenteeRepository menteeRepository;  // Private dependency
    private final UserRepository userRepository;
    private final MenteeMapper menteeMapper;
    
    // Public methods for business logic
    public MenteeDTO createMentee(MenteeDTO dto) { }
    public MenteeDTO getMenteeById(Long id) { }
    // ...
}
```

### Benefits
- **Data hiding:** Fields are private, accessed through getters/setters
- **Controlled access:** Business logic controls how data is modified
- **Validation:** Can add validation in setters or service methods
- **Security:** Password encoding before saving

### Example: Password Encapsulation
```java
public MenteeDTO createMentee(MenteeDTO dto) {
    // Password is encoded internally, not exposed
    mentee.setPassword(passwordEncoder.encode(dto.getPassword()));
    // ...
}
```

---

## 3. Trừu tượng (Abstraction) ⭐⭐⭐

### Implementation

#### Repository Layer - Interface Abstraction
```java
@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {
    Optional<Mentee> findByStudentId(String studentId);
    Boolean existsByStudentId(String studentId);
}
```

#### Service Layer - Business Logic Abstraction
```java
@Service
public class MenteeService {
    // Abstract away complex business logic
    public MenteeDTO createMentee(MenteeDTO dto) {
        // Complex validation and business rules hidden
        validateUsername();
        validateEmail();
        validateStudentId();
        encodePassword();
        saveToDatabase();
        return result;
    }
}
```

#### DTO Layer - Data Abstraction
```java
@Data
public class MentorMenteeRegistrationDTO {
    // Only expose necessary fields to client
    private Long mentorId;
    private String mentorName;
    private String mentorExpertise;
    // ... hide internal implementation details
}
```

### Benefits
- **Simplified interface:** Client only sees necessary operations
- **Implementation hiding:** Database operations hidden behind repository
- **Flexibility:** Can change implementation without affecting clients
- **Separation of concerns:** Each layer has clear responsibility

### Layered Abstraction
```
Controller Layer  → Service Layer  → Repository Layer  → Database
     ↓                   ↓                  ↓
  REST API         Business Logic      Data Access
```

---

## 4. Đa hình (Polymorphism) ⭐⭐⭐

### Implementation

#### Method Overriding (Runtime Polymorphism)
```java
// In User class (parent)
@Data
public abstract class User {
    // Common behavior
}

// In Admin, Mentor, Mentee (children)
// Each can override toString(), equals(), etc.
@Data
@EqualsAndHashCode(callSuper = true)
public class Mentor extends User {
    // Specific implementation
}
```

#### Dependency Injection Polymorphism
```java
// Interface
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}

// Implementation
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Custom implementation
    }
}

// Usage - Spring injects the implementation
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;  // Polymorphic
}
```

#### Repository Polymorphism
```java
// All repositories extend JpaRepository
public interface MenteeRepository extends JpaRepository<Mentee, Long> { }
public interface MentorRepository extends JpaRepository<Mentor, Long> { }

// Can be used polymorphically
private final JpaRepository repository;  // Can be any repository
```

#### Mapper Polymorphism
```java
// MapStruct generates implementations at compile time
@Mapper(componentModel = "spring")
public interface MenteeMapper {
    MenteeDTO toDTO(Mentee mentee);
    Mentee toEntity(MenteeDTO dto);
}

// Spring injects the generated implementation
@RequiredArgsConstructor
public class MenteeService {
    private final MenteeMapper mapper;  // Polymorphic usage
}
```

### Benefits
- **Flexibility:** Can swap implementations easily
- **Extensibility:** Add new implementations without changing client code
- **Dependency Inversion:** Depend on abstractions, not concrete classes
- **Testability:** Easy to mock interfaces for testing

---

## Additional OOP Concepts Applied

### 5. Association Relationships (Unidirectional)

#### Many-to-One (Unidirectional)
```java
// Chỉ join entity chứa references, entity cha KHÔNG chứa collections
@Entity
public class Mentor extends User {
    // KHÔNG có Set<MentorSubject> hoặc Set<MentorMenteeRegistration>
    private String expertise;
    private Integer yearsOfExperience;
    private String bio;
}

@Entity
public class MentorMenteeRegistration {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;
}

@Entity
public class MentorSubject {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
```

#### Truy vấn thông qua Repository
```java
// Để lấy subjects của mentor, sử dụng MentorSubjectRepository
List<MentorSubject> mentorSubjects = mentorSubjectRepository.findByMentorIdWithSubject(mentorId);

// Để lấy registrations của mentee, sử dụng MentorMenteeRegistrationRepository
List<MentorMenteeRegistration> registrations = registrationRepository.findByMenteeIdWithMentor(menteeId);
```

### 6. Composition (Join Entities)
```java
// Join entities chứa references đến các entity liên quan
// Entity cha KHÔNG chứa collections để tránh circular references

@Entity
public class MentorMenteeRegistration {
    // Composed of Mentor and Mentee
    @ManyToOne(fetch = FetchType.LAZY)
    private Mentor mentor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Mentee mentee;
    
    // Without Mentor and Mentee, registration has no meaning
}

@Entity
public class MentorSubject {
    // Composed of Mentor and Subject
    @ManyToOne(fetch = FetchType.LAZY)
    private Mentor mentor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
}
```

### 7. Dependency Injection
```java
@Service
@RequiredArgsConstructor  // Constructor injection
public class AuthService {
    // Dependencies injected via constructor
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
}
```

---

## Design Patterns Applied

### 1. Repository Pattern
```java
@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {
    // Abstraction of data access layer
}
```

### 2. Service Pattern
```java
@Service
public class MenteeService {
    // Business logic separated from controllers
}
```

### 3. DTO Pattern
```java
// Separate data transfer from entities
public class MenteeDTO {
    // Only fields needed for transfer
}
```

### 4. Mapper Pattern
```java
@Mapper(componentModel = "spring")
public interface MenteeMapper {
    // Separate conversion logic
    MenteeDTO toDTO(Mentee entity);
}
```

### 5. Builder Pattern
```java
Admin admin = Admin.builder()
    .username("admin")
    .email("admin@study.com")
    .department("IT")
    .build();
```

### 6. Factory Pattern
```java
// Spring's Dependency Injection acts as a factory
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

## SOLID Principles

### S - Single Responsibility Principle
Each class has one responsibility:
- `MenteeService` - Mentee business logic
- `MenteeRepository` - Mentee data access
- `MenteeMapper` - Mentee entity-DTO mapping

### O - Open/Closed Principle
- Entities can be extended (inheritance) without modification
- New features added through new classes, not modifying existing ones

### L - Liskov Substitution Principle
- `Admin`, `Mentor`, `Mentee` can be used wherever `User` is expected
- Subtypes preserve parent behavior

### I - Interface Segregation Principle
- Repositories have specific methods, not one giant interface
- Each mapper interface is specific to one entity

### D - Dependency Inversion Principle
- Services depend on repository interfaces, not concrete implementations
- High-level modules don't depend on low-level modules

---

## Code Organization (Layered Architecture)

```
┌─────────────────────────────────────┐
│     Controller Layer (REST API)     │  ← User interaction
├─────────────────────────────────────┤
│     Service Layer (Business Logic)  │  ← Business rules
├─────────────────────────────────────┤
│  Repository Layer (Data Access)     │  ← Database operations
├─────────────────────────────────────┤
│     Entity Layer (Domain Model)     │  ← Data structure
└─────────────────────────────────────┘
         ↓
    PostgreSQL Database
```

Each layer:
- Has clear responsibility
- Depends on lower layers only
- Uses abstraction (interfaces)
- Can be tested independently

---

## Summary

Hệ thống Study Management áp dụng đầy đủ các nguyên lý OOP:

✅ **Kế thừa:** User → Admin/Mentor/Mentee hierarchy
✅ **Đóng gói:** Private fields, controlled access via methods
✅ **Trừu tượng:** Layered architecture, interfaces, DTOs
✅ **Đa hình:** Interface implementation, method overriding

Kết hợp với:
- Design patterns (Repository, Service, DTO, Builder)
- SOLID principles
- Clean architecture
- Spring Framework best practices

Tạo nên một hệ thống:
- Dễ maintain và extend
- Testable
- Scalable
- Professional và production-ready

