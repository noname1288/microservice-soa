package com.soa.task_service.service;

import com.soa.task_service.dto.request.CreateTaskRequest;
import com.soa.task_service.dto.request.email.ListMemberResponse;
import com.soa.task_service.dto.request.email.Receiver;
import com.soa.task_service.dto.request.email.RequestGetListMember;
import com.soa.task_service.dto.request.email.RequestSendEmail;
import com.soa.task_service.dto.response.CheckingLeaderReponse;
import com.soa.task_service.dto.response.TaskResponse;
import com.soa.task_service.entity.Task;
import com.soa.task_service.entity.TaskAssignee;
import com.soa.task_service.exception.AppException;
import com.soa.task_service.exception.ErrorCode;
import com.soa.task_service.mapper.TaskMapper;
import com.soa.task_service.reposity.TaskAssigneeRepository;
import com.soa.task_service.reposity.TaskRepository;
import com.soa.task_service.reposity.httpclient.NotificationClient;
import com.soa.task_service.reposity.httpclient.TeamClient;
import com.soa.task_service.reposity.httpclient.UserClient;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;
    TaskAssigneeRepository taskAssigneeRepository;
    TaskMapper taskMapper;
    TeamClient teamClient;
    NotificationClient notificationClient;
    UserClient userClient;

    //create new task
    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        //Kiểm tra quyền admin
        CheckingLeaderReponse checkingLeaderReponse = teamClient.isLeader(
                request.getCreateBy(), request.getTeamId()
        );

        if (Boolean.FALSE.equals(checkingLeaderReponse.getIsLeader())) {
            throw new AppException(ErrorCode.NOT_PERMISSION);
        }
        //Lưu task
        Task task = Task.builder()
                .taskName(request.getTaskName())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .createAt(LocalDateTime.now())
                .createBy(request.getCreateBy()) // parse from jwt
                .teamId(request.getTeamId())
                .build();

        if (taskRepository.existsByTaskName(task.getTaskName())) { // Kiểm tra tên task đã tồn tại hay chưa?
            throw new AppException(ErrorCode.TASK_EXISTED);
        }

        taskRepository.save(task);

        //Lưu người được giao việc
        List<Long> assigneeUserIds = request.getAssigneeUserIds();
        List<TaskAssignee> assignees = new ArrayList<>();

        if (assigneeUserIds != null && !assigneeUserIds.isEmpty()) {
            assignees = request.getAssigneeUserIds().stream()
                    .map(id -> new TaskAssignee(null, task.getId(), id))
                    .toList();

            taskAssigneeRepository.saveAll(assignees);

        }

        TaskResponse response = taskMapper.toTaskResponse(task); //mapper
        taskMapper.mapAssignees(response,task , assignees);

        // lấy thông tin receiver từ user service
        RequestGetListMember requestGetListMember = RequestGetListMember.builder()
                .listIdUser(assigneeUserIds)
                .build();
        ListMemberResponse listMemberResponse = userClient.listMembers(requestGetListMember);
        List<Receiver> receivers = listMemberResponse.getMembers();
        //send email
        RequestSendEmail requestSendEmail = RequestSendEmail.builder()
                .Receivers(receivers)
                .titleTask(task.getTaskName())
                .descriptionTask(task.getDescription())
                .dueDate(task.getDueDate().toString())
                .build();
        
        notificationClient.sendEmail(requestSendEmail);

        return response;
    }

    //get a task by id: Long
    @Override
    public TaskResponse getTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_EXISTED));

        List<TaskAssignee> taskAssignees = taskAssigneeRepository.findByTaskId(taskId);

        TaskResponse response = taskMapper.toTaskResponse( task);
        taskMapper.mapAssignees(response,task,taskAssignees);
        return response;
    }

    //get all task
    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> taskListResult = taskRepository.findAll();

//        if (taskListResult.isEmpty()) {}

        List<TaskResponse> taskResponseList = new ArrayList<>();

        for (Task task : taskListResult) {
            TaskResponse response = taskMapper.toTaskResponse(task);
            List<TaskAssignee> taskAssignees = taskAssigneeRepository.findByTaskId(task.getId());
            taskMapper.mapAssignees(response, task, taskAssignees);

            taskResponseList.add(response);
        }
        return taskResponseList;
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId))
            throw new AppException(ErrorCode.TASK_NOT_EXISTED);

        //xoa task assignees truoc
        //catch: neu nhu Task chua giao viec cho ai
        taskAssigneeRepository.deleteByTaskId(taskId);
        taskRepository.deleteById(taskId);
    }


}
