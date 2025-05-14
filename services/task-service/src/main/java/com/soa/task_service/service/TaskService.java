package com.soa.task_service.service;

import com.soa.task_service.dto.request.CreateTaskRequest;
import com.soa.task_service.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest request);

    TaskResponse getTask(Long taskId);

    List<TaskResponse> getAllTasks();

    void deleteTask(Long taskId);
}
