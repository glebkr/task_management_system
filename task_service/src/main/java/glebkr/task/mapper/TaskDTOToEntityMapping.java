package glebkr.task.mapper;

import org.springframework.stereotype.Component;

import glebkr.task.dto.TaskDTO;
import glebkr.task.entity.Task;

@Component
public class TaskDTOToEntityMapping {
    public Task mapTaskDTOtoEntity(TaskDTO taskDto) {
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .memberId(taskDto.getMemberId())
                .dueDate(taskDto.getDueDate())
                .priority(taskDto.getPriority())
                .status(taskDto.getStatus())
                .build();
    }
}
