package glebkr.task.api;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import glebkr.task.dto.TaskDTO;
import glebkr.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/api/v1/task")
@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;
    private final CacheManager cacheManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(key = "'allTasks'", value = "tasks")
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO);
        Cache cache = cacheManager.getCache("task");
        if (cache != null) {
            cache.put(createdTask.getId(), createdTask);
        }
        return createdTask;
    }

    @GetMapping
    @Cacheable(key = "'allTasks'", value = "tasks")
    public List<TaskDTO> getAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{taskId}")
    @Cacheable(key = "#taskId", value = "task")
    public TaskDTO getTask(@PathVariable("taskId") UUID taskId) {
        return taskService.findTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    @Caching(put = @CachePut(key = "#taskId", value = "task"),
            evict = @CacheEvict(key = "'allTasks'", value = "tasks"))
    public TaskDTO updateTask(@PathVariable("taskId") UUID taskId, @Valid @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @PatchMapping("/{taskId}")
    @Caching(put = @CachePut(key = "#taskId", value = "task"),
            evict = @CacheEvict(key = "'allTasks'", value = "tasks"))
    public TaskDTO updateTaskStatus(@PathVariable("taskId") UUID taskId, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTaskPartially(taskId, taskDTO);
    }

    @DeleteMapping("/{taskId}")
    @Caching(evict = {@CacheEvict(key = "#taskId", value = "task"), @CacheEvict(key = "'allTasks'", value = "tasks")})
    public void deleteTask(@PathVariable("taskId") UUID taskId) {
        taskService.deleteTaskById(taskId);
    }

}
