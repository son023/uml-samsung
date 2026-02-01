# Study Client Configuration Guide

## Quick Start

### Windows Users

1. Open Command Prompt or PowerShell
2. Navigate to the Client directory
3. Run: `run.bat`

### Linux/Mac Users

1. Open Terminal
2. Navigate to the Client directory
3. Make script executable: `chmod +x run.sh`
4. Run: `./run.sh`

## Custom API Server

### Windows
```cmd
run.bat http://your-server:8080
```

### Linux/Mac
```bash
./run.sh http://your-server:8080
```

## Test Credentials

Make sure your backend has these users initialized (via DataInitializer):

### Admin User
- Username: `admin`
- Password: `admin123`

### Mentor Users
- Username: `mentor1` / Password: `mentor123`
- Username: `mentor2` / Password: `mentor123`

### Mentee Users
- Username: `mentee1` / Password: `mentee123`
- Username: `mentee2` / Password: `mentee123`
- Username: `mentee3` / Password: `mentee123`

## Common Issues

### 1. Connection Refused
**Problem**: Cannot connect to backend server

**Solution**:
- Ensure the backend Spring Boot application is running
- Check the API URL (default: http://localhost:8080)
- Verify the port is not blocked by firewall

### 2. Build Failed
**Problem**: Maven build fails

**Solution**:
- Ensure Maven is installed: `mvn -version`
- Check internet connection (for downloading dependencies)
- Delete `target/` folder and rebuild

### 3. Login Failed
**Problem**: Cannot login with credentials

**Solution**:
- Verify the backend is running and accessible
- Check if users are initialized in database
- Ensure password meets minimum length (6 characters)

### 4. Registration Failed (Duplicate)
**Problem**: "Registration failed: Duplicate registration"

**Solution**:
- Each mentee can only register once with each mentor
- Check existing registrations in the "View All Registrations" tab
- Try registering with a different mentor

## Application Features by User Type

### All Users (After Login)
- View all mentor-mentee registrations
- Search and filter registrations
- View registration details
- Logout

### MENTEE Users (Additional)
- Create new registration with mentor
- Specify purpose of registration
- View registration status

### MENTOR Users
- View registrations where they are the mentor
- (Future: Approve/Reject registrations - can be added)

### ADMIN Users
- View all registrations
- (Future: Manage users - can be added)

## Architecture

```
┌─────────────────┐         HTTPS/REST         ┌─────────────────┐
│                 │    ◄─────────────────────►  │                 │
│  Java Swing     │      (OkHttp Client)        │  Spring Boot    │
│  Client         │                             │  Backend        │
│  (This App)     │      JSON (Gson)            │  (Study)        │
│                 │                             │                 │
└─────────────────┘                             └─────────────────┘
                                                        │
                                                        ▼
                                                ┌─────────────────┐
                                                │   PostgreSQL    │
                                                │   Database      │
                                                └─────────────────┘
```

## API Communication

- **Protocol**: HTTP/HTTPS
- **Format**: JSON
- **Authentication**: JWT (Bearer Token)
- **Timeout**: 30 seconds

## Development Setup

### Prerequisites
- JDK 17+
- Maven 3.6+
- IDE with Lombok support (IntelliJ IDEA, Eclipse, VS Code)

### Import into IDE
1. Import as Maven project
2. Install Lombok plugin
3. Enable annotation processing
4. Build project

### Running in IDE
Run the main class: `com.example.client.StudyClientApplication`

## Extending the Application

### Adding New Features

1. **Add new DTO**: Create in `dto/` package
2. **Add API method**: Extend `ApiClient.java`
3. **Create UI**: Add new panel in `ui/` package
4. **Integrate**: Add to `MainDashboard.java`

### Example: Add Delete Registration Feature

```java
// In ApiClient.java
public void deleteRegistration(Long id) throws IOException {
    // Implementation
}

// In RegistrationsPanel.java
private void deleteRegistration() {
    // Add delete button and handler
}
```

## Support

For technical support or questions:
- Check the README.md file
- Review backend API documentation
- Check application logs
