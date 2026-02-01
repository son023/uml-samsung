# Study Client - Complete Project Structure

```
Client/
│
├── 📚 Documentation Files (9 files)
│   ├── INDEX.md                          # Documentation index (start here!)
│   ├── QUICKSTART.md                     # 5-minute quick start guide
│   ├── README.md                         # Complete English documentation
│   ├── HUONG_DAN.md                      # Vietnamese guide (Hướng dẫn tiếng Việt)
│   ├── SETUP.md                          # Setup & troubleshooting guide
│   ├── PROJECT_SUMMARY.md                # Project overview & summary
│   ├── VISUAL_GUIDE.md                   # UI/UX visual guide
│   └── (this file)                       # Project structure visualization
│
├── 🔧 Build & Run Files (4 files)
│   ├── pom.xml                           # Maven build configuration
│   ├── run.bat                           # Windows run script (build + run)
│   ├── run.sh                            # Linux/Mac run script (build + run)
│   ├── run-quick.bat                     # Quick run (Windows, no build)
│   └── .gitignore                        # Git ignore rules
│
├── 📁 Maven Configuration
│   └── .mvn/
│       └── .gitkeep                      # Keep directory in git
│
└── 📁 Source Code (src/)
    └── main/
        ├── 📁 java/com/example/client/
        │   │
        │   ├── 🚀 StudyClientApplication.java     # Main entry point
        │   │
        │   ├── 📦 dto/                            # Data Transfer Objects (5 files)
        │   │   ├── AuthResponse.java              # Login response
        │   │   ├── ErrorResponse.java             # Error handling
        │   │   ├── LoginRequest.java              # Login request
        │   │   ├── MentorMenteeRegistrationDTO.java # Registration data
        │   │   └── PageResponse.java              # Pagination wrapper
        │   │
        │   ├── 🔧 service/                        # Services (1 file)
        │   │   └── ApiClient.java                 # REST API client (OkHttp)
        │   │
        │   └── 🎨 ui/                             # User Interface (4 files)
        │       ├── LoginFrame.java                # Login screen
        │       ├── MainDashboard.java             # Main dashboard with tabs
        │       ├── RegistrationDialog.java        # Registration dialog
        │       └── RegistrationsPanel.java        # Registrations table view
        │
        └── 📁 resources/
            └── simplelogger.properties            # Logging configuration

```

## 📊 Project Statistics

### Files Count
- **Documentation**: 8 markdown files
- **Build Scripts**: 4 files (1 pom.xml + 3 run scripts)
- **Java Source**: 11 files (1 main + 5 DTOs + 1 service + 4 UI)
- **Resources**: 1 properties file
- **Total**: 24 files

### Lines of Code (Approximate)
- **Java Code**: ~2,000 lines
- **Documentation**: ~3,500 lines
- **Configuration**: ~150 lines
- **Total**: ~5,650 lines

### Package Structure
```
com.example.client
├── StudyClientApplication     # Entry point
├── dto                        # 5 classes
├── service                    # 1 class
└── ui                         # 4 classes
```

## 🎯 Key Components Breakdown

### 1. Entry Point
```
StudyClientApplication.java
├── Initializes API client
├── Shows welcome message
├── Opens LoginFrame
└── Handles application lifecycle
```

### 2. Data Transfer Objects (DTOs)
```
dto/
├── AuthResponse              # JWT token + user info
├── ErrorResponse             # Error message handling
├── LoginRequest              # Username + password
├── MentorMenteeRegistrationDTO # Registration data
└── PageResponse<T>           # Generic pagination wrapper
```

### 3. API Service
```
ApiClient.java
├── login(LoginRequest)                          # POST /api/auth/login
├── logout()                                     # POST /api/auth/logout
├── createRegistration(DTO)                      # POST /api/registrations
├── getAllRegistrations(params)                  # GET /api/registrations
├── getRegistrationById(id)                      # GET /api/registrations/{id}
└── deleteRegistration(id)                       # DELETE /api/registrations/{id}
```

### 4. User Interface Components
```
ui/
├── LoginFrame
│   ├── Username field
│   ├── Password field
│   ├── Login button
│   └── Validation
│
├── MainDashboard
│   ├── Header (user info + logout)
│   ├── Tab 1: View All Registrations
│   ├── Tab 2: Register with Mentor (MENTEE only)
│   └── Tab 3: About
│
├── RegistrationsPanel
│   ├── Search box
│   ├── Status filter
│   ├── Data table (8 columns)
│   ├── Pagination (Previous/Next)
│   └── Refresh button
│
└── RegistrationDialog
    ├── Mentor ID field
    ├── Purpose text area
    ├── Submit button
    └── Cancel button
```

## 🔄 Data Flow

```
User Action
    ↓
UI Component (Swing)
    ↓
ApiClient (OkHttp)
    ↓
HTTP Request (JSON via Gson)
    ↓
Backend REST API
    ↓
HTTP Response (JSON)
    ↓
ApiClient (Gson deserialization)
    ↓
DTO Objects
    ↓
UI Component Update
    ↓
User sees result
```

## 🛠️ Technology Stack

| Layer | Technology | Purpose |
|-------|------------|---------|
| UI | Java Swing | Desktop interface |
| HTTP Client | OkHttp 4.12.0 | REST API calls |
| JSON | Gson 2.10.1 | Serialization/deserialization |
| Logging | SLF4J 2.0.9 | Application logging |
| Build | Maven 3.6+ | Dependency management |
| Language | Java 17 | Programming language |
| IDE Support | Lombok 1.18.30 | Reduce boilerplate |

## 📡 API Communication

### Authentication Flow
```
1. User enters credentials
2. LoginRequest → POST /api/auth/login
3. Backend validates
4. Backend returns AuthResponse with JWT
5. Client stores token in ApiClient
6. All subsequent requests include: Authorization: Bearer <token>
```

### Registration Flow
```
1. Mentee clicks "Create New Registration"
2. Enters Mentor ID + Purpose
3. RegistrationDTO → POST /api/registrations
4. Backend validates (checks duplicate, mentor exists)
5. Backend creates registration (status = PENDING)
6. Backend returns created RegistrationDTO
7. Client shows success + refreshes list
```

### View Registrations Flow
```
1. User opens "View All Registrations" tab
2. Client → GET /api/registrations?page=0&size=10
3. Backend queries database with filters
4. Backend returns PageResponse<RegistrationDTO>
5. Client populates table
6. User can search/filter → repeat from step 2
```

## 🎨 UI Design Patterns

### Layout Managers Used
- **BorderLayout**: Main panels (header/center/footer)
- **FlowLayout**: Button panels
- **GridBagLayout**: Form layouts
- **BoxLayout**: Vertical stacking

### Swing Components Used
- JFrame, JDialog, JPanel
- JTextField, JPasswordField, JTextArea
- JButton, JLabel
- JTable, DefaultTableModel
- JScrollPane
- JTabbedPane
- JComboBox
- JOptionPane (dialogs)

### Design Principles
- ✅ Separation of concerns (UI / Service / DTO)
- ✅ Async operations (SwingWorker for API calls)
- ✅ Loading states (disable buttons during operations)
- ✅ Error handling (try-catch with user messages)
- ✅ Validation (client-side before API calls)

## 🔐 Security Features

- JWT token-based authentication
- Token stored in memory (not persisted)
- Password field masking
- Token cleared on logout
- HTTPS support (just change URL)
- Session timeout (24 hours default)

## 🚀 Running the Application

### Quick Start (Windows)
```cmd
cd Client
run.bat
```

### Quick Start (Linux/Mac)
```bash
cd Client
chmod +x run.sh
./run.sh
```

### With Custom API URL
```bash
run.bat http://your-server:8080
./run.sh http://your-server:8080
```

### Manual Build
```bash
mvn clean package
java -jar target/study-client-1.0.0.jar
```

## 📚 Documentation Guide

| Document | Purpose | Audience |
|----------|---------|----------|
| INDEX.md | Navigation hub | All users |
| QUICKSTART.md | Fast start | New users |
| README.md | Complete guide | All users |
| HUONG_DAN.md | Vietnamese guide | Vietnamese speakers |
| SETUP.md | Setup & troubleshooting | Developers/Admins |
| PROJECT_SUMMARY.md | Overview | Developers/Managers |
| VISUAL_GUIDE.md | UI guide | Users/Designers |
| STRUCTURE.md | This file | Developers |

## ✅ Feature Checklist

### Completed Features ✅
- [x] Login with JWT authentication
- [x] View all registrations (paginated)
- [x] Search registrations
- [x] Filter by status
- [x] Create registration (Mentee)
- [x] Logout
- [x] Error handling
- [x] Loading states
- [x] Input validation
- [x] Responsive UI
- [x] Documentation (8 files)
- [x] Build scripts
- [x] Configuration

### Future Enhancements 🔜
- [ ] Approve/Reject registration (Mentor)
- [ ] Delete registration (Mentee)
- [ ] View registration details dialog
- [ ] User profile management
- [ ] Export to CSV/PDF
- [ ] Email notifications
- [ ] Multi-language UI
- [ ] Dark mode theme

## 🎓 Code Quality

### Best Practices Applied
- ✅ Proper exception handling
- ✅ Async UI operations (SwingWorker)
- ✅ Clean code structure
- ✅ Meaningful variable names
- ✅ Comments where needed
- ✅ Logging for debugging
- ✅ Validation before API calls
- ✅ User feedback for all actions

### Design Patterns
- **Singleton-like**: ApiClient instance passed around
- **DTO Pattern**: Separate data objects
- **MVC-like**: Separation of UI, Service, Data
- **Observer**: Swing event listeners
- **Factory**: SwingWorker for background tasks

## 📞 Support & Contribution

For support:
1. Check documentation (INDEX.md → find your topic)
2. Review error messages and logs
3. Check backend connectivity
4. Contact development team

For contribution:
1. Follow existing code style
2. Update documentation
3. Test thoroughly
4. Submit pull request

---

**Project Status**: ✅ Complete and Ready for Production

**Version**: 1.0.0  
**Created**: 2026-01-31  
**License**: © 2026 Study System  
**Repository**: UML SS/Client
