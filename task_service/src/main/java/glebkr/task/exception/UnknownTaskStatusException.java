package glebkr.task.exception;

import glebkr.task.model.TaskStatusEnum;

public class UnknownTaskStatusException extends RuntimeException {
    public UnknownTaskStatusException(TaskStatusEnum status) {
        super(STR."Status \{status} is unknown");
    }
}
