package glebkr.task.service;

import java.util.List;
import java.util.UUID;

import glebkr.task.dto.TaskDTO;
import glebkr.task.model.TaskStatusEnum;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> findAllTasks();

    TaskDTO findTaskById(UUID taskId);

    TaskDTO updateTask(UUID taskId, TaskDTO taskDTO);

    void deleteTaskById(UUID taskId);

    TaskDTO updateTaskStatus(UUID taskId, TaskDTO taskDTO);
}
