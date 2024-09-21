package glebkr.task.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import glebkr.task.dto.TaskAnalyticsDTO;
import glebkr.task.dto.TaskDTO;
import glebkr.task.entity.Task;
import glebkr.task.exception.TaskNotFoundException;
import glebkr.task.exception.UnknownTaskStatusException;
import glebkr.task.mapper.TaskDTOToEntityMapping;
import glebkr.task.mapper.TaskEntityToDTOMapping;
import glebkr.task.mapper.TaskEntityToOutboxEntityMapping;
import glebkr.task.model.TaskStatusEnum;
import glebkr.task.outbox.entity.Outbox;
import glebkr.task.outbox.repository.OutboxRepository;
import glebkr.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final OutboxRepository outboxRepository;
    private final TaskEntityToDTOMapping taskEntityToDTOMapping;
    private final TaskDTOToEntityMapping taskDTOtoEntityMapping;
    private final TaskEntityToOutboxEntityMapping taskEntityToOutboxEntityMapping;

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        taskDTO.setCreateDate(LocalDateTime.now());
        Task task = taskDTOtoEntityMapping.mapTaskDTOtoEntity(taskDTO);
        Task createdTask = taskRepository.save(task);

        Outbox outbox = taskEntityToOutboxEntityMapping.mapTaskEntityToOutboxEntity(createdTask);
        outboxRepository.save(outbox);

        return taskEntityToDTOMapping.mapTaskEntityToDto(createdTask);
    }

    @Override
    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll().stream().map(taskEntityToDTOMapping::mapTaskEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findTasksByInterval(LocalDateTime startDate, LocalDateTime endDate) {
        List<Task> foundTasksByInterval = taskRepository.findTasksByInterval(startDate, endDate);
        return foundTasksByInterval.stream().map(taskEntityToDTOMapping::mapTaskEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskAnalyticsDTO> getTaskAnalyticsByInterval(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.getTasksAnalyticsByInterval(startDate, endDate);
    }

    @Override
    public List<TaskAnalyticsDTO> getTaskAnalyticsByMemberId(UUID memberId) {
        return taskRepository.getTasksAnalyticsByMemberId(memberId);
    }

    @Override
    public TaskDTO findTaskById(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return taskEntityToDTOMapping.mapTaskEntityToDto(task);
    }

    @Override
    public TaskDTO updateTask(UUID taskId, TaskDTO taskDTO) {
        Task foundTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        foundTask.setTitle(taskDTO.getTitle());
        foundTask.setDescription(taskDTO.getDescription());
        foundTask.setPriority(taskDTO.getPriority());
        foundTask.setStatus(taskDTO.getStatus());
        foundTask.setDueDate(taskDTO.getDueDate());
        Task savedTask = taskRepository.save(foundTask);
        return taskEntityToDTOMapping.mapTaskEntityToDto(savedTask);
    }

    @Override
    public TaskDTO updateTaskPartially(UUID taskId, TaskDTO taskDTO) {
        Task foundTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        if (taskDTO.getTitle() != null) {
            foundTask.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            foundTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getPriority() != null) {
            foundTask.setPriority(taskDTO.getPriority());
        }
        if (taskDTO.getStatus() != null) {
            foundTask.setStatus(taskDTO.getStatus());
        }
        if (taskDTO.getDueDate() != null) {
            foundTask.setDueDate(taskDTO.getDueDate());
        }

        Task savedTask = taskRepository.save(foundTask);
        return taskEntityToDTOMapping.mapTaskEntityToDto(savedTask);
    }

    @Override
    public TaskDTO updateTaskStatus(UUID taskId, TaskStatusEnum newStatus) {
        Task foundTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        switch (newStatus) {
            case OPEN -> {
                foundTask.setStatus(newStatus);
                break;
            }
            case IN_PROGRESS -> {
                foundTask.setStatus(newStatus);
                foundTask.setStartDate(LocalDateTime.now());
                break;
            }
            case RESOLVED -> {
                foundTask.setStatus(newStatus);
                foundTask.setResolvingDate(LocalDateTime.now());
                break;
            }
            default -> throw new UnknownTaskStatusException(newStatus);
        }

        Task updatedTask = taskRepository.save(foundTask);

        return taskEntityToDTOMapping.mapTaskEntityToDto(updatedTask);
    }

    @Override
    public void deleteTaskById(UUID taskId) {
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.deleteById(taskId);
    }

}
