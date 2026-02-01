# Hướng Dẫn Sử Dụng Study Client

## Giới Thiệu

Ứng dụng Study Client là một giao diện desktop được xây dựng bằng Java Swing để quản lý hệ thống Mentor-Mentee. Ứng dụng kết nối với backend qua REST API sử dụng HTTPS.

## Các Chức Năng Chính

### 1. Đăng Nhập (Login)
- Nhập username và password
- Hệ thống hỗ trợ 3 loại người dùng: ADMIN, MENTOR, MENTEE
- Sau khi đăng nhập thành công, nhận JWT token để xác thực các request tiếp theo

### 2. Xem Danh Sách Đăng Ký (View All Registrations)
- Xem tất cả các đăng ký mentor-mentee
- Tìm kiếm theo tên mentor/mentee
- Lọc theo trạng thái: PENDING, APPROVED, REJECTED, COMPLETED
- Phân trang dữ liệu (10 records mỗi trang)

### 3. Đăng Ký Với Mentor (Mentee Registration)
- Chỉ dành cho người dùng có vai trò MENTEE
- Nhập Mentor ID muốn đăng ký
- Nhập mục đích đăng ký
- Trạng thái ban đầu sẽ là PENDING, chờ mentor phê duyệt

## Cài Đặt và Chạy Ứng Dụng

### Yêu Cầu Hệ Thống
- Java 17 trở lên
- Maven 3.6 trở lên
- Backend Study đang chạy (mặc định: http://localhost:8080)

### Các Bước Chạy Ứng Dụng

#### Trên Windows:
```cmd
cd Client
run.bat
```

Hoặc chạy với API server tùy chỉnh:
```cmd
run.bat http://server-cua-ban:8080
```

#### Trên Linux/Mac:
```bash
cd Client
chmod +x run.sh
./run.sh
```

Hoặc với API server tùy chỉnh:
```bash
./run.sh http://server-cua-ban:8080
```

### Build Thủ Công

```bash
cd Client
mvn clean package
java -jar target/study-client-1.0.0.jar
```

## Tài Khoản Test

Backend có sẵn các tài khoản sau (được tạo tự động):

### Admin
- Username: `admin`
- Password: `admin123`

### Mentor
- Username: `mentor1`, Password: `mentor123`
- Username: `mentor2`, Password: `mentor123`

### Mentee
- Username: `mentee1`, Password: `mentee123`
- Username: `mentee2`, Password: `mentee123`
- Username: `mentee3`, Password: `mentee123`

## Hướng Dẫn Chi Tiết

### Bước 1: Đăng Nhập

1. Khởi động ứng dụng
2. Nhập username (ví dụ: `mentee1`)
3. Nhập password (ví dụ: `mentee123`)
4. Click nút "Login"
5. Nếu thành công, sẽ chuyển đến Dashboard

### Bước 2: Xem Danh Sách Đăng Ký

1. Tab "View All Registrations" sẽ hiển thị mặc định
2. Bảng hiển thị các thông tin:
   - ID: Mã đăng ký
   - Mentor ID: Mã mentor
   - Mentor Name: Tên mentor
   - Mentee ID: Mã mentee
   - Mentee Name: Tên mentee
   - Status: Trạng thái (PENDING/APPROVED/REJECTED/COMPLETED)
   - Registered At: Thời gian đăng ký
   - Purpose: Mục đích đăng ký

3. Sử dụng tìm kiếm:
   - Nhập từ khóa vào ô "Search"
   - Chọn trạng thái trong dropdown "Status"
   - Click "Search"

4. Phân trang:
   - Click "< Previous" để về trang trước
   - Click "Next >" để sang trang sau
   - Xem thông tin trang hiện tại ở giữa

### Bước 3: Đăng Ký Với Mentor (Chỉ cho Mentee)

1. Đăng nhập với tài khoản MENTEE
2. Click vào tab "Register with Mentor"
3. Click nút "Create New Registration"
4. Trong dialog hiện ra:
   - Nhập Mentor ID (tìm trong danh sách đăng ký)
   - Nhập mục đích đăng ký
   - Click "Submit Registration"
5. Nếu thành công, sẽ thấy thông báo và đăng ký mới xuất hiện trong danh sách

### Bước 4: Đăng Xuất

1. Click nút "Logout" ở góc trên bên phải
2. Xác nhận đăng xuất
3. Quay về màn hình đăng nhập

## Cấu Trúc Dự Án

```
Client/
├── pom.xml                          # Maven configuration
├── README.md                        # English documentation
├── HUONG_DAN.md                     # Tài liệu tiếng Việt
├── SETUP.md                         # Setup guide
├── run.bat                          # Windows run script
├── run.sh                           # Linux/Mac run script
└── src/
    └── main/
        ├── java/
        │   └── com/example/client/
        │       ├── StudyClientApplication.java    # Entry point
        │       ├── dto/                           # Data Transfer Objects
        │       │   ├── AuthResponse.java
        │       │   ├── ErrorResponse.java
        │       │   ├── LoginRequest.java
        │       │   ├── MentorMenteeRegistrationDTO.java
        │       │   └── PageResponse.java
        │       ├── service/
        │       │   └── ApiClient.java            # REST API client
        │       └── ui/                           # Giao diện
        │           ├── LoginFrame.java           # Màn hình đăng nhập
        │           ├── MainDashboard.java        # Dashboard chính
        │           ├── RegistrationDialog.java   # Dialog đăng ký
        │           └── RegistrationsPanel.java   # Bảng danh sách
        └── resources/
            └── simplelogger.properties           # Logging config
```

## Công Nghệ Sử Dụng

- **Java Swing**: Xây dựng giao diện desktop
- **OkHttp**: Client HTTP để gọi REST API
- **Gson**: Chuyển đổi JSON
- **Lombok**: Giảm boilerplate code
- **SLF4J**: Logging
- **Maven**: Quản lý dependencies

## API Endpoints

| Endpoint | Method | Mô Tả | Yêu Cầu Auth |
|----------|--------|-------|--------------|
| `/api/auth/login` | POST | Đăng nhập | Không |
| `/api/auth/logout` | POST | Đăng xuất | Có |
| `/api/registrations` | GET | Lấy danh sách đăng ký | Có |
| `/api/registrations` | POST | Tạo đăng ký mới | Có (MENTEE) |
| `/api/registrations/{id}` | GET | Xem chi tiết đăng ký | Có |
| `/api/registrations/{id}` | DELETE | Xóa đăng ký | Có (MENTEE) |

## Xử Lý Lỗi Thường Gặp

### 1. "Connection Refused"
**Nguyên nhân**: Không kết nối được backend

**Giải pháp**:
- Kiểm tra backend có đang chạy không
- Xác nhận URL API đúng (mặc định: http://localhost:8080)
- Kiểm tra firewall

### 2. "Login Failed"
**Nguyên nhân**: Sai username/password hoặc backend lỗi

**Giải pháp**:
- Kiểm tra username và password
- Đảm bảo backend đã khởi tạo dữ liệu (DataInitializer)
- Xem log backend để biết chi tiết lỗi

### 3. "Registration Failed: Duplicate"
**Nguyên nhân**: Đã đăng ký với mentor này rồi

**Giải pháp**:
- Mỗi mentee chỉ được đăng ký 1 lần với 1 mentor
- Kiểm tra danh sách đăng ký hiện có
- Thử đăng ký với mentor khác

### 4. "Mentor ID must be a valid number"
**Nguyên nhân**: Nhập sai định dạng Mentor ID

**Giải pháp**:
- Mentor ID phải là số nguyên
- Xem ID trong cột "Mentor ID" ở bảng danh sách

## Mở Rộng Ứng Dụng

### Thêm Chức Năng Mới

Ví dụ: Thêm chức năng xóa đăng ký

1. **Thêm method trong ApiClient.java**:
```java
public void deleteRegistration(Long id) throws IOException {
    // Implementation already exists
}
```

2. **Thêm button trong RegistrationsPanel.java**:
```java
JButton deleteButton = new JButton("Delete");
deleteButton.addActionListener(e -> deleteSelectedRegistration());
```

3. **Implement handler**:
```java
private void deleteSelectedRegistration() {
    int selectedRow = registrationsTable.getSelectedRow();
    if (selectedRow >= 0) {
        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        // Call API to delete
        apiClient.deleteRegistration(id);
        loadRegistrations(); // Refresh
    }
}
```

## Screenshots

### Màn Hình Đăng Nhập
- Form đăng nhập với username/password
- Nút Login và Exit
- Validation lỗi nhập liệu

### Dashboard Chính
- Header với thông tin user và nút Logout
- Tabs: View All Registrations, Register with Mentor (cho mentee), About
- Bảng dữ liệu với search, filter, pagination

### Dialog Đăng Ký
- Form nhập Mentor ID và Purpose
- Validation và xử lý lỗi
- Thông báo thành công/thất bại

## Liên Hệ & Hỗ Trợ

Nếu gặp vấn đề khi sử dụng ứng dụng:
1. Kiểm tra file README.md và SETUP.md
2. Xem log trong console
3. Kiểm tra backend có lỗi không
4. Liên hệ team phát triển

## License

© 2026 Study System. All rights reserved.
