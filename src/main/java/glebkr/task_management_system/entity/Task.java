package glebkr.task_management_system.entity;

import java.util.Date;

import glebkr.task_management_system.model.PriorityEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Date dueDate;
    private PriorityEnum priority;
}
