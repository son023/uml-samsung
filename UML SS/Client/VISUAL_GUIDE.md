# 🎨 Visual Application Guide

## 📱 Application Screenshots & Flow

### 1️⃣ Login Screen (LoginFrame)

```
┌─────────────────────────────────────────────┐
│    Mentor-Mentee Study System              │
│                                             │
│    Username:  [________________]            │
│                                             │
│    Password:  [________________]            │
│                                             │
│         [Login]    [Exit]                   │
│                                             │
└─────────────────────────────────────────────┘
```

**Features**:
- Username input field
- Password input field (masked as dots)
- Login button (green)
- Exit button (red)
- Enter key triggers login
- Validation messages
- Loading indicator during login

**Validation**:
- ❌ Username required
- ❌ Password required (min 6 characters)
- ✅ Success → Go to Dashboard
- ❌ Error → Show error message

---

### 2️⃣ Main Dashboard (MainDashboard)

```
┌──────────────────────────────────────────────────────────────────┐
│ Mentor-Mentee Study System │ User: mentee1 (MENTEE)  [Logout]  │
├──────────────────────────────────────────────────────────────────┤
│                                                                   │
│  [View All Registrations] [Register with Mentor] [About]         │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                                                             │ │
│  │  Current Tab Content Shows Here                           │ │
│  │                                                             │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
```

**Header**:
- App title
- Current user info (username + role)
- Logout button

**Tabs** (available based on role):
1. **View All Registrations** - All users
2. **Register with Mentor** - MENTEE only
3. **About** - All users

---

### 3️⃣ View All Registrations Tab (RegistrationsPanel)

```
┌──────────────────────────────────────────────────────────────────┐
│ Search: [________] Status: [All ▼] [Search] [Refresh]           │
├──────────────────────────────────────────────────────────────────┤
│ ID │Mentor│Mentor Name│Mentee│Mentee Name│Status  │Date        │ │
├────┼──────┼───────────┼──────┼───────────┼────────┼────────────┤ │
│ 1  │  1   │John Doe   │  3   │Jane Smith │PENDING │2026-01-31  │ │
│ 2  │  2   │Bob Lee    │  4   │Tom Brown  │APPROVED│2026-01-30  │ │
│ 3  │  1   │John Doe   │  5   │Ann White  │REJECTED│2026-01-29  │ │
│ ...│ ...  │ ...       │ ...  │ ...       │ ...    │ ...        │ │
├──────────────────────────────────────────────────────────────────┤
│          [< Previous]  Page 1 of 5  [Next >]                     │
└──────────────────────────────────────────────────────────────────┘
```

**Features**:
- Search box (search by name)
- Status filter dropdown
- Data table with 8 columns
- Pagination controls
- 10 records per page
- Auto-refresh capability

**Columns**:
1. ID - Registration ID
2. Mentor ID - Mentor's user ID
3. Mentor Name - Mentor's full name
4. Mentee ID - Mentee's user ID
5. Mentee Name - Mentee's full name
6. Status - PENDING/APPROVED/REJECTED/COMPLETED
7. Registered At - Date & time
8. Purpose - Registration purpose (truncated)

**Actions**:
- Click "Search" to filter
- Click "Refresh" to reload
- Navigate pages with Previous/Next

---

### 4️⃣ Register with Mentor Tab (Only for MENTEE)

```
┌──────────────────────────────────────────────────────────────────┐
│                                                                   │
│             Register with a Mentor                                │
│                                                                   │
│  As a mentee, you can register with a mentor to receive          │
│  guidance and support.                                            │
│                                                                   │
│  To register:                                                     │
│  1. Find the mentor you want to work with                        │
│  2. Note their Mentor ID                                         │
│  3. Click the 'Create New Registration' button below             │
│  4. Enter the Mentor ID and your purpose                         │
│  5. Submit and wait for approval                                 │
│                                                                   │
│                                                                   │
│              [Create New Registration]                            │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
```

**Features**:
- Instructions for mentees
- "Create New Registration" button (green, centered)
- Opens Registration Dialog on click

---

### 5️⃣ Registration Dialog (RegistrationDialog)

```
┌─────────────────────────────────────────┐
│   Mentor-Mentee Registration           │
├─────────────────────────────────────────┤
│                                         │
│  Mentor ID:    [________]               │
│                                         │
│  Purpose:      ┌──────────────────────┐ │
│                │                      │ │
│                │                      │ │
│                │                      │ │
│                │                      │ │
│                └──────────────────────┘ │
│                                         │
│  Note: Enter the Mentor ID you wish    │
│  to register with.                     │
│                                         │
│  [Submit Registration]  [Cancel]        │
│                                         │
└─────────────────────────────────────────┘
```

**Features**:
- Mentor ID input field
- Purpose text area (multi-line)
- Submit button (green)
- Cancel button (gray)
- Validation
- Success/error messages

**Validation**:
- ❌ Mentor ID required (must be number)
- ❌ Purpose required
- ✅ Success → Show confirmation + refresh list
- ❌ Error → Show error message

---

### 6️⃣ About Tab

```
┌──────────────────────────────────────────────────────────────────┐
│                                                                   │
│                  About Study System                               │
│                                                                   │
│  Mentor-Mentee Study System                                       │
│  Version 1.0.0                                                    │
│                                                                   │
│  This application provides a client interface for the             │
│  Study System, which facilitates mentor-mentee                    │
│  relationships in educational settings.                           │
│                                                                   │
│  Features:                                                        │
│  • User authentication with JWT tokens                            │
│  • View all mentor-mentee registrations                           │
│  • Mentee registration with mentors                               │
│  • Search and filter registrations                                │
│  • Paginated data display                                         │
│                                                                   │
│  Technical Stack:                                                 │
│  • Java Swing for UI                                              │
│  • OkHttp for REST API communication                              │
│  • Gson for JSON serialization                                    │
│  • HTTPS/REST communication with backend                          │
│                                                                   │
│  © 2026 Study System. All rights reserved.                        │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
```

---

## 🎯 User Journey Examples

### Journey 1: Mentee Creates Registration

```
1. Start App
   ↓
2. Login as mentee1 / mentee123
   ↓
3. See Dashboard (2 tabs available)
   ↓
4. Click "View All Registrations"
   ↓
5. Note a Mentor ID (e.g., ID = 1)
   ↓
6. Click "Register with Mentor" tab
   ↓
7. Click "Create New Registration"
   ↓
8. Enter:
   - Mentor ID: 1
   - Purpose: "Need help with Java programming"
   ↓
9. Click "Submit Registration"
   ↓
10. See success message
   ↓
11. Auto-switch to "View All Registrations"
   ↓
12. See new registration with status "PENDING"
```

### Journey 2: View and Search Registrations

```
1. Login as any user
   ↓
2. See "View All Registrations" tab (default)
   ↓
3. Browse all registrations in table
   ↓
4. Want to find specific mentor?
   ↓
5. Enter "John" in Search box
   ↓
6. Select status "APPROVED" in dropdown
   ↓
7. Click "Search"
   ↓
8. See filtered results
   ↓
9. Navigate pages with Previous/Next
   ↓
10. Click "Refresh" to see latest data
```

### Journey 3: Logout

```
1. From any screen in Dashboard
   ↓
2. Click "Logout" button (top-right)
   ↓
3. Confirm logout in dialog
   ↓
4. Token cleared
   ↓
5. Return to Login screen
```

---

## 🎨 Color Scheme

| Element | Color | Hex Code |
|---------|-------|----------|
| Primary | Blue | #2196F3 |
| Success | Green | #4CAF50 |
| Danger | Red | #F44336 |
| Secondary | Gray | #9E9E9E |
| Header Background | Blue | #2196F3 |
| Text on Header | White | #FFFFFF |
| Table Header | Light Gray | #F0F0F0 |

---

## 📐 Component Sizes

| Component | Size |
|-----------|------|
| Login Frame | 450 x 300 |
| Main Dashboard | 1200 x 700 |
| Registration Dialog | 500 x 400 |
| Input Fields | Height 25px |
| Buttons | 120 x 35 (standard) |
| Large Buttons | 180-250 x 35-45 |
| Table Row Height | 25px |

---

## 🔤 Fonts

| Element | Font |
|---------|------|
| Title | Arial Bold 18-20 |
| Subtitles | Arial Bold 16 |
| Labels | Arial Plain 14 |
| Input Fields | Arial Plain 13-14 |
| Buttons | Arial Bold 13-14 |
| Table Header | Arial Bold 12 |
| Table Data | Arial Plain 12 |
| Info Text | Arial Italic 11 |

---

## 🎬 Animation & Feedback

### Loading States
- Login button shows "Logging in..." during auth
- Submit button shows "Submitting..." during registration
- Buttons disabled during API calls

### Success Messages
```
┌─────────────────────────────────────┐
│         ✓ Success                   │
│                                     │
│  Registration submitted successfully│
│  Registration ID: 5                 │
│  Status: PENDING                    │
│                                     │
│              [OK]                   │
└─────────────────────────────────────┘
```

### Error Messages
```
┌─────────────────────────────────────┐
│         ✗ Error                     │
│                                     │
│  Login failed:                      │
│  Invalid username or password       │
│                                     │
│              [OK]                   │
└─────────────────────────────────────┘
```

### Confirmation Dialogs
```
┌─────────────────────────────────────┐
│      ? Confirm Logout               │
│                                     │
│  Are you sure you want to logout?  │
│                                     │
│         [Yes]    [No]               │
└─────────────────────────────────────┘
```

---

## 🎯 Accessibility Features

- ✅ Keyboard navigation (Tab, Enter)
- ✅ Enter key submits forms
- ✅ Clear error messages
- ✅ Disabled state for buttons during loading
- ✅ Focus management (auto-focus on first field)
- ✅ Password masking
- ✅ Readable fonts and sizes
- ✅ Color contrast for readability

---

## 💡 Tips for Users

1. **Finding Mentor ID**: Go to "View All Registrations" and look in the "Mentor ID" column
2. **Checking Status**: Registrations start as PENDING and await mentor approval
3. **Search Tips**: Search works on mentor/mentee names (partial match)
4. **Refresh Data**: Click Refresh to see latest registrations
5. **Duplicate Prevention**: You can only register once with each mentor
6. **Session Management**: JWT token expires after 24 hours (configurable)

---

**This visual guide helps you understand the UI/UX of the Study Client Application!** 🎉
