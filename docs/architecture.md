# System Architecture

## Overview
### Mục đích của hệ thống tạo và giao việc

Hệ thống "Tạo và Giao việc" là một phần cốt lõi trong các nền tảng quản lý công việc, dự án, hoặc cộng tác nhóm. Mục tiêu chính là cho phép quản trị viên trong một team có thể tạo công việc mới, phân công người thực hiện, và đảm bảo việc thông báo được thực hiện một cách chính xác.

1. **Đảm bảo phân quyền rõ ràng**: Hệ thống chỉ cho phép người dùng có quyền quản trị viên trong team tạo và giao việc. Tránh việc người không đủ thẩm quyền tạo công việc không phù hợp.

2. **Thu thập và lưu trữ thông tin công việc**: Thu thập đầy đủ thông tin: tiêu đề, mô tả, thời hạn, người được giao,...(Thiết kế chi tiết hộ)Lưu trữ vào hệ thống cơ sở dữ liệu để đảm bảo có thể tra cứu, chỉnh sửa, hoặc theo dõi.

3. **Hỗ trợ chọn và giao việc cho nhiều người**: Hỗ trợ lựa chọn một hoặc nhiều người trong team để giao việc. Mỗi người nhận sẽ có thông tin cụ thể về nhiệm vụ được giao.

4. **Tự động thông báo đến người được giao việc**: Ngay sau khi tạo công việc, hệ thống gửi thông báo đến người được giao thông qua: Email,Notification trong ứng dụng

### Các thành phần chính và trách nhiệm của chúng

1. **Dịch vụ Gateway** (`gateway-service`):
    - Là điểm vào duy nhất cho client (UI/Web/Mobile).
    - Xác thực token (JWT) trước khi định tuyến yêu cầu.
    - Định tuyến các request đến đúng service.
    - Ghi log, theo dõi, và có thể chặn (rate-limiting / throttling).

2. **Dịch vụ xác thực**(`authentication-service`):
    - Quản lý đăng nhập, đăng ký, và tạo JWT token.
    - Cung cấp API để xác thực người dùng.
    - Phân quyền người dùng theo vai trò (Admin, Member).

3. **Dịch vụ người dùng**(`user-service`):
    - Quản lý thông tin người dùng: ID, tên, email, avatar...
    - Trả về danh sách thành viên của một team.
    - (Optional) Cung cấp API tìm kiếm thành viên để giao việc.

4. **Dịch vụ nhóm**(`team-service`):
    - Quản lý các team làm việc.
    - Quản lý vai trò của người dùng trong từng team.
    - API kiểm tra xem user có phải quản trị viên của team không (isAdmin(teamId, userId)).

5. **Dịch vụ công việc**(`task-service`):
    - Nhận request tạo công việc mới từ quản trị viên.
    - Lưu thông tin công việc: tiêu đề, mô tả, deadline.
    - Lưu danh sách người thực hiện.
    - Gửi yêu cầu sang Notification Service sau khi lưu thành công.
    - Cung cấp API để lấy danh sách công việc, chi tiết công việc.

6. **Dịch vụ thông báo**(`notification-service`):
    - Nhận yêu cầu gửi thông báo từ Task Service.
    - Gửi thông báo đến các người dùng được giao việc:
    - Qua email.
    - In-app notification.
    - (Optional) Firebase push notification.
    - Ghi log lịch sử gửi thông báo.

Mỗi dịch vụ được thiết kế với một trách nhiệm duy nhất, duy trì cơ sở dữ liệu riêng và giao tiếp với các dịch vụ khác thông qua các API REST được xác định. Thiết kế này đảm bảo kết nối lỏng lẻo, triển khai độc lập và các nhóm phát triển tập trung.
## System Components
- **Service A**: Brief description of its functionality and role.
- **Service B**: Brief description of its functionality and role.
- **API Gateway**: Explain its role in routing and managing requests.

## Communication
- Describe how services interact (e.g., REST APIs, message queues).
- Mention internal networking (e.g., Docker Compose service names).

## Data Flow
- Explain the flow of data between services and the gateway.
- Include any external dependencies (e.g., databases, third-party APIs).

## Diagram
- Reference a high-level architecture diagram (place in `docs/asset/`).

## Scalability & Fault Tolerance
- Briefly discuss how the system can scale or handle failures.