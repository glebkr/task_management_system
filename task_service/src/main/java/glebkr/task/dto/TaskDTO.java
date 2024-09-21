package glebkr.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import glebkr.task.model.TaskPriorityEnum;
import glebkr.task.model.TaskStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private UUID id;
    @NotEmpty
    private String title;
    private String description;
    private UUID memberId;
    @NotNull
    private TaskPriorityEnum priority;
    @NotNull
    private TaskStatusEnum status;
    private LocalDateTime createDate;
    private LocalDateTime dueDate;
    private LocalDateTime startDate;
    private LocalDateTime resolvingDate;
}
