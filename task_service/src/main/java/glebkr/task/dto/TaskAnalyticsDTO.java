package glebkr.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAnalyticsDTO {
    private Long tasksAmount;
    private Integer averageHourCompletionTime;
}
