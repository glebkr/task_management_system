package glebkr.task.entity;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import glebkr.task.model.TaskPriorityEnum;
import glebkr.task.model.TaskStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    @NotNull
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskPriorityEnum priority;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatusEnum status;
}
