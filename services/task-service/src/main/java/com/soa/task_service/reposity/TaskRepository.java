package com.soa.task_service.reposity;

import com.soa.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsById(Long id);
    boolean existsByTaskName(String taskName);
}
