package com.soa.task_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTaskRequest {
    private String taskName;
    private String description;
    private LocalDateTime dueDate;
    private Long teamId;
    private List<Long> assigneeUserIds;
//    private String createAt;
    private Long createBy; // user ask to create task
//    @Size(min = 2, message = "enum key like "TASK_EXISTED" ")
}
