# User Service

Service quản lý tài khoản người dùng và xác thực hệ thống quản lý công việc.

## Tổng quan

User Service chịu trách nhiệm:

- Đăng ký người dùng mới
- Xác thực đăng nhập và cấp JWT token
- Làm mới token
- Kiểm tra token hợp lệ (validate)
- Trả về thông tin người dùng theo danh sách ID

## Công nghệ sử dụng

- Spring Boot 3 (JDK 21)
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Docker

## Cấu trúc thư mục
user-service/
├── controller/ # Định nghĩa các REST API
├── dto/ # Đối tượng truyền dữ liệu (Request / Response)
├── entity/ # Entity ánh xạ bảng DB
├── repository/ # Interface thao tác DB
├── service/ # Business logic
├── util/ # JWT util và mã hóa
└── UserServiceApplication.java

## Cài đặt

### Yêu cầu hệ thống
- JDK 21
- Docker + Docker Compose
- Gradle 8.13+

### Cài đặt & Build

```bash
./gradlew bootJar
docker build -t user-service

## Cấu hình

### Biến môi trường
cầu hình trong application.properties

spring.application.name=user-service
server.port=8081
server.servlet.context-path=/user



spring.datasource.url=jdbc:mysql://localhost:3306/user_service
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect


spring.jpa.show-sql=true

jwt.secret=Khs94nMCuY2shjvN1XZf4QeBknPciJTmMB8QZXU7IwoMplwYvYVuPlqY8VHz59d7543515453645465
jwt.access.expiration=6000000       
jwt.refresh.expiration=86400000 

logging.level.org.springframework.security=DEBUG

## Chạy service

### Development

./gradlew bootRun

### Production

docker-compose up -d user-service user-db

