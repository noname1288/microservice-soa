package com.soa.task_service.mapper;

import com.soa.task_service.dto.response.TaskResponse;
import com.soa.task_service.entity.Task;
import com.soa.task_service.entity.TaskAssignee;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    // Chuyển đổi Task (không bao gồm assignees)
    @Mapping(target = "assigneeIds", ignore = true) // sẽ xử lý thủ công
    TaskResponse toTaskResponse(Task task);

    // Sau khi ánh xạ xong Task → TaskResponse, bổ sung assignees
    @AfterMapping
    default void mapAssignees(@MappingTarget TaskResponse response,Task task , List<TaskAssignee> assignees) {
        if (assignees != null) {
            List<Long> assigneeListIds = assignees.stream()
                    .map(TaskAssignee::getUserId)
                    .collect(Collectors.toList());
            response.setAssigneeIds(assigneeListIds);
        }
    }
}
