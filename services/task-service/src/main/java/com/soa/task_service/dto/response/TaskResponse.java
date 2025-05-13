package com.soa.task_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
    Long id;
    String taskName;
    String description;
    LocalDateTime dueDate;   // hoặc giữ nguyên LocalDateTime
    LocalDateTime  createAt;
    Long createBy;
    Long teamId;
    List<Long> assigneeIds;
}
