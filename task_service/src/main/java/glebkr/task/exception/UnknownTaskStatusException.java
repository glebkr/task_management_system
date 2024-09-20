package glebkr.task.exception;

import java.util.UUID;

import glebkr.task.model.TaskStatusEnum;

public class UnknownTaskStatusException extends RuntimeException {
    public UnknownTaskStatusException(TaskStatusEnum status) {
        super("Status " + status + " is unknown");
    }
}
