package glebkr.task.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

import glebkr.task.entity.Task;
import glebkr.task.model.TaskPriorityEnum;
import glebkr.task.model.TaskStatusEnum;
import glebkr.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostConstruct
    public void addTasks()  {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Task task = Task.builder()
                .title("add redis caching")
                .description("you need to add redis caching for Task service")
                .dueDate(cal.getTime())
                .priority(TaskPriorityEnum.HIGH)
                .status(TaskStatusEnum.OPEN)
                .build();
        taskRepository.save(task);
    }

}
