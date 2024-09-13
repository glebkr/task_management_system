package glebkr.task_management_system.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

import glebkr.task_management_system.entity.Task;
import glebkr.task_management_system.model.PriorityEnum;
import glebkr.task_management_system.repository.TaskRepository;
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
    public void run()  {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Task task = Task.builder()
                .title("add redis caching")
                .description("you need to add redis caching for Task service")
                .dueDate(cal.getTime())
                .priority(PriorityEnum.MAX)
                .build();
        taskRepository.save(task);
    }

}
