package glebkr.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import glebkr.task.dto.TaskAnalyticsDTO;
import glebkr.task.dto.TaskDTO;
import glebkr.task.model.TaskStatusEnum;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> findAllTasks();

    List<TaskDTO> findTasksByInterval(LocalDateTime startDate, LocalDateTime endDate);

    List<TaskAnalyticsDTO> getTaskAnalyticsByInterval(LocalDateTime startDate, LocalDateTime endDate);

    List<TaskAnalyticsDTO> getTaskAnalyticsByMemberId(UUID memberId);

    TaskDTO findTaskById(UUID taskId);

    TaskDTO updateTask(UUID taskId, TaskDTO taskDTO);

    TaskDTO updateTaskPartially(UUID taskId, TaskDTO taskDTO);

    TaskDTO updateTaskStatus(UUID taskId, TaskStatusEnum newStatus);

    void deleteTaskById(UUID taskId);


}
