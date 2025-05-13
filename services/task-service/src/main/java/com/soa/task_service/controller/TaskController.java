package com.soa.task_service.controller;

import com.soa.task_service.dto.ApiResponse;
import com.soa.task_service.dto.request.CreateTaskRequest;
import com.soa.task_service.dto.response.TaskResponse;
import com.soa.task_service.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;

    @PostMapping("/create")
    ApiResponse<TaskResponse> createTask (@RequestBody CreateTaskRequest request){
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();

        TaskResponse newTaskResponse = taskService.createTask(request);

        apiResponse.setMessage("Create successfully");
        apiResponse.setResult(newTaskResponse);

        return apiResponse;
    }

    @GetMapping("/{task_id}")
    ApiResponse<TaskResponse> getTask (@PathVariable Long task_id){
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(taskService.getTask(task_id));
        return apiResponse;
    }

    @GetMapping("/all")
    ApiResponse<List<TaskResponse>> getAllTasks(){
        ApiResponse<List<TaskResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(taskService.getAllTasks());

        return apiResponse;
    }

    @DeleteMapping("/delete/{taskId}")
    ApiResponse<TaskResponse> deleteTask (@PathVariable Long taskId){
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();

        taskService.deleteTask(taskId);
        apiResponse.setMessage("Delete a task successfully.");

        return apiResponse;
    }
}
