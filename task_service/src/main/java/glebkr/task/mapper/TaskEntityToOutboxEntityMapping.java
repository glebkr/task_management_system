package glebkr.task.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import glebkr.task.entity.Task;
import glebkr.task.outbox.entity.Outbox;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class TaskEntityToOutboxEntityMapping {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public Outbox mapTaskEntityToOutboxEntity(Task task) {
        return Outbox.builder()
                .payload(objectMapper.writeValueAsString(task))
                .createdAt(LocalDate.now())
                .isProcessed(false)
                .build();
    }

}
