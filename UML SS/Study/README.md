# Study Management System - Simplified Version

## Mô tả

Hệ thống đơn giản quản lý đăng ký Mentor-Mentee được xây dựng bằng Java Spring Boot với các tính năng cốt lõi.

## Công nghệ sử dụng

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **MapStruct**
- **Maven**

## Chức năng

### 1. Authentication (All Users) ✅
- `POST /api/auth/login` - Đăng nhập (admin, mentor, mentee)
- `POST /api/auth/logout` - Đăng xuất

### 2. Mentor-Mentee Registration (Mentee) ✅
- `POST /api/registrations` - Mentee đăng ký với mentor
- `DELETE /api/registrations/{id}` - Mentee hủy đăng ký

### 3. View Registrations (All Authenticated Users) ✅
- `GET /api/registrations` - Xem tất cả đăng ký (search, sort, page)
- `GET /api/registrations/{id}` - Xem chi tiết đăng ký

### 4. Mentor Actions ✅
- `PATCH /api/registrations/{id}/status` - Mentor approve/reject đăng ký

## Cấu trúc Database

### Tables

1. **users** - Bảng cha chứa thông tin user
2. **admins** - Join với users
3. **mentors** - Join với users
4. **mentees** - Join với users
5. **mentor_mentee_registrations** - Đăng ký mentor-mentee

## Cài đặt và Chạy

### 1. Tạo Database
```sql
CREATE DATABASE study_db;
```

### 2. Cấu hình `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/study_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Build và Run
```bash
mvn clean install
mvn spring-boot:run
```

Application chạy tại: `http://localhost:8080`

## API Examples

### 1. Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "mentee1",
  "password": "mentee123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 3,
  "username": "mentee1",
  "userType": "MENTEE"
}
```

### 2. Mentee đăng ký với Mentor
```bash
POST /api/registrations
Authorization: Bearer {mentee_token}
Content-Type: application/json

{
  "mentorId": 1,
  "menteeId": 3,
  "purpose": "Muốn học Java từ mentor có kinh nghiệm",
  "notes": "Rảnh vào cuối tuần"
}
```

**Response:**
```json
{
  "id": 1,
  "mentorId": 1,
  "mentorName": "John Smith",
  "mentorExpertise": "Java Programming",
  "menteeId": 3,
  "menteeName": "Alice Johnson",
  "studentId": "ST001",
  "status": "PENDING",
  "registeredAt": "2026-01-31T10:00:00",
  "purpose": "Muốn học Java từ mentor có kinh nghiệm",
  "notes": "Rảnh vào cuối tuần"
}
```

### 3. Xem tất cả đăng ký (All users)
```bash
GET /api/registrations?page=0&size=10&sort=registeredAt,desc
Authorization: Bearer {any_token}
```

**Query Parameters:**
- `search` - Tìm kiếm theo tên mentor/mentee
- `mentorId` - Lọc theo mentor
- `menteeId` - Lọc theo mentee
- `status` - Lọc theo trạng thái (PENDING, APPROVED, REJECTED, COMPLETED)
- `page` - Số trang (default: 0)
- `size` - Số items/trang (default: 10)
- `sort` - Sắp xếp (vd: registeredAt,desc)

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "mentorId": 1,
      "mentorName": "John Smith",
      "mentorExpertise": "Java Programming",
      "menteeId": 3,
      "menteeName": "Alice Johnson",
      "studentId": "ST001",
      "status": "PENDING",
      "registeredAt": "2026-01-31T10:00:00",
      "purpose": "Muốn học Java",
      "notes": "Rảnh cuối tuần"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

### 4. Mentor approve đăng ký
```bash
PATCH /api/registrations/1/status?status=APPROVED&notes=Welcome!
Authorization: Bearer {mentor_token}
```

**Response:**
```json
{
  "id": 1,
  "status": "APPROVED",
  "notes": "Welcome!",
  ...
}
```

## Sample Users

Application tự động tạo users khi chạy lần đầu:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Mentor | mentor1 | mentor123 |
| Mentor | mentor2 | mentor123 |
| Mentee | mentee1 | mentee123 |
| Mentee | mentee2 | mentee123 |

## Testing Flow

### Scenario: Mentee đăng ký với Mentor

1. **Login as Mentee:**
```bash
POST /api/auth/login
{"username": "mentee1", "password": "mentee123"}
```

2. **Đăng ký với Mentor:**
```bash
POST /api/registrations
{
  "mentorId": 1,
  "menteeId": 3,
  "purpose": "Learn Java Programming"
}
```

3. **Login as Mentor:**
```bash
POST /api/auth/login
{"username": "mentor1", "password": "mentor123"}
```

4. **Xem đăng ký pending:**
```bash
GET /api/registrations?mentorId=1&status=PENDING
```

5. **Approve đăng ký:**
```bash
PATCH /api/registrations/1/status?status=APPROVED
```

6. **Anyone xem tất cả registrations:**
```bash
GET /api/registrations
```

## Security

- JWT-based authentication
- Role-based authorization:
  - MENTEE: Có thể đăng ký và hủy đăng ký
  - MENTOR: Có thể approve/reject đăng ký
  - ALL authenticated users: Xem tất cả đăng ký

## OOP Implementation

### Inheritance (Kế thừa)
```
User (Abstract)
  ├── Admin
  ├── Mentor
  └── Mentee
```

### Encapsulation (Đóng gói)
- Private fields trong entities
- Business logic trong service layer

### Abstraction (Trừu tượng)
- Repository interfaces
- Service layer abstracts database operations

### Polymorphism (Đa hình)
- User interface implementations
- Repository pattern

## Project Structure

```
src/main/java/com/example/study/
├── controller/
│   ├── AuthController.java
│   └── MentorMenteeController.java
├── service/
│   ├── AuthService.java
│   ├── CustomUserDetailsService.java
│   └── MentorMenteeRegistrationService.java
├── repository/
│   ├── UserRepository.java
│   ├── AdminRepository.java
│   ├── MentorRepository.java
│   ├── MenteeRepository.java
│   └── MentorMenteeRegistrationRepository.java
├── entity/
│   ├── User.java (Abstract)
│   ├── Admin.java
│   ├── Mentor.java
│   ├── Mentee.java
│   └── MentorMenteeRegistration.java
├── dto/
│   ├── LoginRequest.java
│   ├── AuthResponse.java
│   └── MentorMenteeRegistrationDTO.java
├── mapper/
│   └── MentorMenteeRegistrationMapper.java
└── common/
    ├── config/
    │   ├── SecurityConfig.java
    │   ├── JwtAuthenticationFilter.java
    │   └── DataInitializer.java
    ├── exception/
    │   └── GlobalExceptionHandler.java
    └── util/
        └── JwtUtil.java
```

## Features

✅ JWT Authentication
✅ Role-based Authorization
✅ Mentor-Mentee Registration
✅ View All Registrations
✅ Search, Sort, Pagination
✅ OOP Principles Applied
✅ Clean Architecture
✅ Exception Handling
✅ Sample Data Initialization

---

**Simple, Clean, and Focused! 🎯**
