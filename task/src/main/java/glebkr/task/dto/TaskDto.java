package glebkr.task.dto;

import java.util.Date;

import glebkr.task.model.TaskPriorityEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {
    private Integer id;
    private String title;
    private String description;
    private Date dueDate;
    private TaskPriorityEnum priority;
}
