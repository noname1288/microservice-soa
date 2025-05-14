package com.soa.task_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    Long id;

    @Column(name = "task_name",nullable = false)
    String taskName;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(name = "due_date")
    LocalDateTime dueDate; //thời gian hết hạn công việc

    @Column(name = "create_at")
    LocalDateTime createAt; //thời gian tạo công việc

    @Column(name = "created_by_user")
    Long createBy; // ID người tạo công việc // from JWT

    @Column(name = "team_id")
    Long teamId; // Id của team thực hiện task

//    Nếu bạn muốn:

//    Truy vấn: “Lấy task và tất cả assignee của nó” trong 1 lần (task.getAssignees())
//    Dùng cascade khi xóa task → xóa luôn assignees
//    Dùng trong DTO/response JSON để trả ra danh sách assignee
//    // ✅ Ánh xạ ngược sang danh sách assignee
//    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<TaskAssignee> assignees = new ArrayList<>();

}
