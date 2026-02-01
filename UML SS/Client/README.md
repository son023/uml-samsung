# Study Client Application

Java Swing desktop client for the Mentor-Mentee Study System. This application provides a user-friendly interface to interact with the Study System backend via REST API.

## Features

- **User Authentication**: Secure login with JWT token-based authentication
- **View All Registrations**: Browse all mentor-mentee registrations with search and filter capabilities
- **Mentee Registration**: Mentees can register with mentors
- **Pagination**: Efficient data display with pagination support
- **Modern UI**: Clean and intuitive Java Swing interface

## Requirements

- Java 17 or higher
- Maven 3.6 or higher
- Running Study backend server (default: http://localhost:8080)

## Project Structure

```
Client/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── client/
                        ├── StudyClientApplication.java  # Main entry point
                        ├── dto/                         # Data Transfer Objects
                        │   ├── AuthResponse.java
                        │   ├── ErrorResponse.java
                        │   ├── LoginRequest.java
                        │   ├── MentorMenteeRegistrationDTO.java
                        │   └── PageResponse.java
                        ├── service/                     # API Services
                        │   └── ApiClient.java          # REST API client
                        └── ui/                          # UI Components
                            ├── LoginFrame.java
                            ├── MainDashboard.java
                            ├── RegistrationDialog.java
                            └── RegistrationsPanel.java
```

## Building the Application

### Using Maven

```bash
cd Client
mvn clean package
```

This will create a JAR file in the `target/` directory.

## Running the Application

### Option 1: Run with Maven

```bash
mvn exec:java -Dexec.mainClass="com.example.client.StudyClientApplication"
```

### Option 2: Run the JAR file

```bash
java -jar target/study-client-1.0.0.jar
```

### Option 3: Run with custom API URL

```bash
java -jar target/study-client-1.0.0.jar http://your-server:8080
```

Or with Maven:

```bash
mvn exec:java -Dexec.mainClass="com.example.client.StudyClientApplication" -Dexec.args="http://your-server:8080"
```

## Usage Guide

### 1. Login

- Launch the application
- Enter your username and password
- Click "Login"
- Supported user types: ADMIN, MENTOR, MENTEE

### 2. View All Registrations

- After login, you'll see the "View All Registrations" tab
- Browse through all mentor-mentee registrations
- Use the search bar to filter by mentor/mentee name
- Filter by registration status (PENDING, APPROVED, REJECTED, COMPLETED)
- Navigate through pages using Previous/Next buttons

### 3. Register with Mentor (Mentee Only)

- Click on the "Register with Mentor" tab
- Click "Create New Registration" button
- Enter the Mentor ID (you can find this in the registrations list)
- Enter your purpose for registration
- Click "Submit Registration"
- Your registration will be in PENDING status until approved

### 4. Logout

- Click the "Logout" button in the top-right corner
- You'll be returned to the login screen

## Configuration

### Default Settings

- **API Base URL**: http://localhost:8080
- **Page Size**: 10 registrations per page
- **Connection Timeout**: 30 seconds

### Changing API URL

Pass the API URL as a command-line argument:

```bash
java -jar target/study-client-1.0.0.jar http://production-server:8080
```

## Dependencies

- **OkHttp 4.12.0**: HTTP client for REST API calls
- **Gson 2.10.1**: JSON serialization/deserialization
- **Lombok 1.18.30**: Reduce boilerplate code
- **SLF4J 2.0.9**: Logging framework

## API Endpoints Used

| Endpoint | Method | Description | Auth Required |
|----------|--------|-------------|---------------|
| `/api/auth/login` | POST | User login | No |
| `/api/auth/logout` | POST | User logout | Yes |
| `/api/registrations` | GET | Get all registrations (paginated) | Yes |
| `/api/registrations` | POST | Create new registration | Yes (MENTEE) |
| `/api/registrations/{id}` | GET | Get registration by ID | Yes |
| `/api/registrations/{id}` | DELETE | Delete registration | Yes (MENTEE) |

## Troubleshooting

### Connection Refused

- Ensure the backend server is running
- Check if the API URL is correct
- Verify firewall settings

### Login Failed

- Verify username and password
- Check backend server logs
- Ensure user exists in database

### Registration Failed

- Verify the Mentor ID exists
- Ensure you're logged in as a MENTEE
- Check for duplicate registrations (one mentee can only register once with each mentor)

## Development

### IDE Setup

1. Import as Maven project
2. Ensure Lombok plugin is installed
3. Set Java 17 as project SDK

### Building from Source

```bash
git clone <repository-url>
cd Client
mvn clean install
```

## License

© 2026 Study System. All rights reserved.

## Support

For issues or questions, please contact the development team.
