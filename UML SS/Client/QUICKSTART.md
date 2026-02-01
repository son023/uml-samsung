# Quick Start Guide

## 🚀 Fastest Way to Run

### Windows
```cmd
cd Client
run.bat
```

### Linux/Mac
```bash
cd Client
chmod +x run.sh
./run.sh
```

## 📝 Login Credentials (Test Users)

After the app starts, use these credentials:

**Mentee** (Can create registrations):
- Username: `mentee1`
- Password: `mentee123`

**Mentor**:
- Username: `mentor1`
- Password: `mentor123`

**Admin**:
- Username: `admin`
- Password: `admin123`

## ✨ What You Can Do

### As MENTEE:
1. ✅ View all mentor-mentee registrations
2. ✅ Register with a mentor (Enter Mentor ID from the list)
3. ✅ Search and filter registrations

### As MENTOR or ADMIN:
1. ✅ View all mentor-mentee registrations
2. ✅ Search and filter registrations

## 🔧 Requirements

- ☕ Java 17+ installed
- 🔧 Maven 3.6+ installed  
- 🖥️ Backend server running at `http://localhost:8080`

## 📊 Flow Example

1. **Start Backend**: Run the Study Spring Boot application
2. **Start Client**: Run `run.bat` (Windows) or `./run.sh` (Linux/Mac)
3. **Login**: Use `mentee1` / `mentee123`
4. **View Registrations**: See the list in the first tab
5. **Create Registration**: 
   - Go to "Register with Mentor" tab
   - Click "Create New Registration"
   - Enter Mentor ID: `1` (or any mentor ID from the list)
   - Enter Purpose: "Need help with Java programming"
   - Submit
6. **See Result**: Registration appears in the list with status "PENDING"

## ⚠️ Common Issues

**"Connection Refused"**
→ Make sure the backend is running at http://localhost:8080

**"Build Failed"**  
→ Check Maven is installed: `mvn -version`

**"Login Failed"**
→ Verify backend is running and has test data initialized

## 📚 More Info

- Full documentation: See `README.md`
- Vietnamese guide: See `HUONG_DAN.md`
- Setup details: See `SETUP.md`

## 🎯 Architecture

```
┌──────────────┐         REST API          ┌──────────────┐
│              │  ◄─────────────────────►  │              │
│ Java Swing   │     (HTTP/HTTPS)          │ Spring Boot  │
│ Client       │      JWT Auth             │ Backend      │
│ (This App)   │                           │ (Study)      │
└──────────────┘                           └──────────────┘
```

**Communication**: REST API with JSON (Gson) over HTTP/HTTPS  
**Authentication**: JWT Bearer Token  
**HTTP Client**: OkHttp 4.12.0

---

**Ready to go? Run `run.bat` or `./run.sh` now! 🎉**
