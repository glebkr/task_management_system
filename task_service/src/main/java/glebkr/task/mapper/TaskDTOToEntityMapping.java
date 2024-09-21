package glebkr.task.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import glebkr.task.dto.TaskDTO;
import glebkr.task.entity.Task;

@Component
public class TaskDTOToEntityMapping {
    public Task mapTaskDTOtoEntity(TaskDTO taskDto) {
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .memberId(taskDto.getMemberId())
                .priority(taskDto.getPriority())
                .status(taskDto.getStatus())
                .createDate(taskDto.getCreateDate())
                .dueDate(taskDto.getDueDate())
                .startDate(taskDto.getStartDate())
                .resolvingDate(taskDto.getResolvingDate())
                .build();
    }
}
