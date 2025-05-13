package com.soa.task_service.dto.request;

import jakarta.validation.constraints.Size;
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
//    private String createBy;
//    @Size(min = 2, message = "enum key like "TASK_EXISTED" ")
}
