package com.soa.task_service.reposity;

import com.soa.task_service.entity.TaskAssignee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long> {
    List<TaskAssignee> findByTaskId(Long taskId);

    void deleteByTaskId(Long taskId); // tiện cho delete nếu cần
}
