# Study Client - Project Summary

## 📋 Overview

A Java Swing desktop client application for the Mentor-Mentee Study System. Communicates with Spring Boot backend via REST API using HTTPS.

## 🎯 Features Implemented

### ✅ Login (Đăng nhập)
- Username/password authentication
- JWT token-based session management
- Support for ADMIN, MENTOR, MENTEE roles
- Error handling and validation

### ✅ Mentor-Mentee Registration (Đăng ký Mentor-Mentee)
- Mentee can register with mentors
- Input: Mentor ID, Purpose
- Validation and error handling
- Success confirmation

### ✅ View All Registrations (Xem tất cả đăng ký)
- Paginated table view (10 records per page)
- Columns: ID, Mentor ID, Mentor Name, Mentee ID, Mentee Name, Status, Date, Purpose
- Search functionality
- Filter by status (PENDING, APPROVED, REJECTED, COMPLETED)
- Previous/Next navigation

## 📁 Project Structure

```
Client/
├── pom.xml                                    # Maven dependencies
├── README.md                                  # English documentation
├── HUONG_DAN.md                              # Vietnamese guide
├── QUICKSTART.md                             # Quick start guide
├── SETUP.md                                  # Setup instructions
├── .gitignore                                # Git ignore rules
├── run.bat                                   # Windows run script
├── run.sh                                    # Linux/Mac run script
├── run-quick.bat                             # Quick run (Windows)
└── src/
    └── main/
        ├── java/com/example/client/
        │   ├── StudyClientApplication.java   # 🚀 Main entry point
        │   │
        │   ├── dto/                          # 📦 Data Transfer Objects
        │   │   ├── AuthResponse.java         # Login response
        │   │   ├── ErrorResponse.java        # Error handling
        │   │   ├── LoginRequest.java         # Login request
        │   │   ├── MentorMenteeRegistrationDTO.java  # Registration data
        │   │   └── PageResponse.java         # Pagination wrapper
        │   │
        │   ├── service/                      # 🔧 Services
        │   │   └── ApiClient.java            # REST API client (OkHttp)
        │   │
        │   └── ui/                           # 🎨 UI Components
        │       ├── LoginFrame.java           # Login screen
        │       ├── MainDashboard.java        # Main dashboard with tabs
        │       ├── RegistrationDialog.java   # Registration dialog
        │       └── RegistrationsPanel.java   # Registrations table view
        │
        └── resources/
            └── simplelogger.properties       # Logging configuration
```

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Programming language |
| Maven | 3.6+ | Build tool & dependency management |
| Java Swing | Built-in | Desktop UI framework |
| OkHttp | 4.12.0 | HTTP client for REST API |
| Gson | 2.10.1 | JSON serialization/deserialization |
| Lombok | 1.18.30 | Reduce boilerplate code |
| SLF4J | 2.0.9 | Logging framework |

## 🔌 API Endpoints Used

| Endpoint | Method | Description | Auth | Role |
|----------|--------|-------------|------|------|
| `/api/auth/login` | POST | User authentication | ❌ No | All |
| `/api/auth/logout` | POST | Logout user | ✅ Yes | All |
| `/api/registrations` | GET | Get all registrations (paginated) | ✅ Yes | All |
| `/api/registrations` | POST | Create new registration | ✅ Yes | MENTEE |
| `/api/registrations/{id}` | GET | Get registration by ID | ✅ Yes | All |
| `/api/registrations/{id}` | DELETE | Delete registration | ✅ Yes | MENTEE |

## 🎨 UI Screens

### 1. LoginFrame
- Username field
- Password field (masked)
- Login button
- Exit button
- Validation messages
- Loading state

### 2. MainDashboard
- Header with user info and logout button
- Tabbed interface:
  - **Tab 1**: View All Registrations (RegistrationsPanel)
  - **Tab 2**: Register with Mentor (only for MENTEE)
  - **Tab 3**: About

### 3. RegistrationsPanel
- Search bar
- Status filter dropdown
- Data table with columns
- Pagination controls (Previous/Next)
- Page indicator
- Refresh button

### 4. RegistrationDialog
- Mentor ID input field
- Purpose text area
- Submit button
- Cancel button
- Validation

## 🔄 Application Flow

```
Start Application
       ↓
   LoginFrame
       ↓
   [Login]
       ↓
  JWT Token Stored
       ↓
 MainDashboard
       ↓
    ┌─────────────┬──────────────────┬────────┐
    ↓             ↓                  ↓        ↓
View Registrations  Create Registration  About  Logout
    ↓                   ↓                       ↓
[Search/Filter]   [Submit Form]         [Return to Login]
    ↓                   ↓
[Pagination]      [Refresh List]
```

## 📡 REST Communication

### Request Format
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "mentorId": 1,
  "menteeId": 3,
  "purpose": "Need help with Java"
}
```

### Response Format
```json
{
  "id": 1,
  "mentorId": 1,
  "mentorName": "John Mentor",
  "menteeId": 3,
  "menteeName": "Jane Mentee",
  "status": "PENDING",
  "registeredAt": "2026-01-31T10:30:00",
  "purpose": "Need help with Java"
}
```

## 🧪 Test Credentials

| Username | Password | Role | Can Register? |
|----------|----------|------|---------------|
| admin | admin123 | ADMIN | ❌ No |
| mentor1 | mentor123 | MENTOR | ❌ No |
| mentor2 | mentor123 | MENTOR | ❌ No |
| mentee1 | mentee123 | MENTEE | ✅ Yes |
| mentee2 | mentee123 | MENTEE | ✅ Yes |
| mentee3 | mentee123 | MENTEE | ✅ Yes |

## 🚀 How to Run

### Prerequisites
```bash
# Check Java
java -version
# Should show Java 17+

# Check Maven
mvn -version
# Should show Maven 3.6+

# Ensure backend is running
# Backend should be at http://localhost:8080
```

### Build and Run
```bash
# Windows
cd Client
run.bat

# Linux/Mac
cd Client
chmod +x run.sh
./run.sh

# With custom API URL
run.bat http://your-server:8080
./run.sh http://your-server:8080
```

### Manual Build
```bash
cd Client
mvn clean package
java -jar target/study-client-1.0.0.jar
```

## 🎯 Key Features by User Role

### All Users
- ✅ Login/Logout
- ✅ View all registrations
- ✅ Search registrations
- ✅ Filter by status
- ✅ Paginate through results

### MENTEE (Additional)
- ✅ Create new registration with mentor
- ✅ Specify purpose of registration
- ✅ View registration status

### Future Enhancements
- 🔜 Mentor can approve/reject registrations
- 🔜 Admin can manage users
- 🔜 View registration history
- 🔜 Export data to CSV/PDF
- 🔜 Email notifications

## ⚙️ Configuration

### Default Settings
- **API URL**: http://localhost:8080
- **Page Size**: 10 records
- **Timeout**: 30 seconds
- **Log Level**: INFO

### Customization
Change in code or pass as command-line argument:
```java
// In StudyClientApplication.java
private static final String DEFAULT_API_URL = "http://localhost:8080";

// Or run with:
java -jar study-client-1.0.0.jar http://custom-server:8080
```

## 🐛 Error Handling

- ✅ Network errors (connection refused, timeout)
- ✅ Authentication errors (invalid credentials)
- ✅ Validation errors (empty fields, invalid format)
- ✅ Business logic errors (duplicate registration)
- ✅ Server errors (500, 400, etc.)

## 📊 Code Statistics

- **Total Classes**: 13
- **UI Components**: 4
- **DTOs**: 5
- **Services**: 1
- **Main Application**: 1
- **Documentation Files**: 5
- **Total Lines of Code**: ~2000+

## 🔐 Security

- JWT token-based authentication
- Token stored in memory (cleared on logout)
- HTTPS support (change URL to https://)
- Password fields masked
- Session management

## 📚 Documentation Files

1. **README.md** - English documentation
2. **HUONG_DAN.md** - Vietnamese guide (Hướng dẫn tiếng Việt)
3. **QUICKSTART.md** - Quick start guide
4. **SETUP.md** - Setup and troubleshooting
5. **PROJECT_SUMMARY.md** - This file (overview)

## ✅ Checklist

- [x] Login functionality
- [x] JWT authentication
- [x] View all registrations
- [x] Pagination
- [x] Search functionality
- [x] Filter by status
- [x] Create registration (Mentee)
- [x] Input validation
- [x] Error handling
- [x] REST API communication
- [x] Modern UI design
- [x] Documentation
- [x] Build scripts
- [x] Test credentials

## 🎓 Learning Resources

- Java Swing: [Oracle Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- OkHttp: [OkHttp Documentation](https://square.github.io/okhttp/)
- Gson: [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)
- REST API: [RESTful API Design](https://restfulapi.net/)

## 📞 Support

For questions or issues:
1. Check documentation files
2. Review backend API documentation
3. Check application logs
4. Contact development team

---

**Project Status**: ✅ Complete and Ready to Use

**Created**: 2026-01-31  
**Version**: 1.0.0  
**License**: © 2026 Study System
