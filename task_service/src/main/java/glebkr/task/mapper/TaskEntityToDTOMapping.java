package glebkr.task.mapper;

import org.springframework.stereotype.Component;

import glebkr.task.dto.TaskDTO;
import glebkr.task.entity.Task;

@Component
public class TaskEntityToDTOMapping {
    public TaskDTO mapTaskEntityToDto(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .memberId(task.getMemberId())
                .priority(task.getPriority())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .startDate(task.getStartDate())
                .resolvingDate(task.getResolvingDate())
                .build();
    }
}
