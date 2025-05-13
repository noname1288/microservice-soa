package com.soa.task_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
@Entity
public class TaskAssignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_assignee_id")
    Long id;

//    @ManyToOne(fetch = FetchType.LAZY) //mối quan hệ nhiều TaskAssignee → 1 Task.
    // fetch = FetchType.LAZY =  không tự động tải Task khi lấy TaskAssignee.
    @JoinColumn(name = "task_id", nullable = false) // foreign key (FK) trỏ đến bảng tasks.
    Long  taskId;

    @Column(name = "user_id", nullable = false)
    Long userId;
}
