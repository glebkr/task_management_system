package glebkr.task.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import glebkr.task.dto.TaskDTO;
import glebkr.task.model.TaskStatusEnum;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> findAllTasks();

    TaskDTO findTaskById(UUID taskId);

    List<TaskDTO> findTasksByInterval(LocalDate startDate, LocalDate resolvingDate);

    TaskDTO updateTask(UUID taskId, TaskDTO taskDTO);

    TaskDTO updateTaskPartially(UUID taskId, TaskDTO taskDTO);

    TaskDTO updateTaskStatus(UUID taskId, TaskStatusEnum newStatus);

    void deleteTaskById(UUID taskId);

}
