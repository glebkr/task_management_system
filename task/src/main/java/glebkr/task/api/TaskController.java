package glebkr.task.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import glebkr.task.entity.Task;
import glebkr.task.service.TaskService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/api/v1/task")
@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/get")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

}
