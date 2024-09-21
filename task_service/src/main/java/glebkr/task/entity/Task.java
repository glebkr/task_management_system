package glebkr.task.entity;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import glebkr.task.model.TaskPriorityEnum;
import glebkr.task.model.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotEmpty
    private String title;
    @Column(length = 500)
    private String description;
    private UUID memberId;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskPriorityEnum priority;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatusEnum status;
    private LocalDateTime createDate;
    private LocalDateTime dueDate;
    private LocalDateTime startDate;
    private LocalDateTime resolvingDate;

}
