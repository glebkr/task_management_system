package glebkr.task_management_system.dto;

import java.util.Date;

import glebkr.task_management_system.model.PriorityEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {

    private Integer id;
    private String title;
    private String description;
    private Date dueDate;
    private PriorityEnum priority;
}
