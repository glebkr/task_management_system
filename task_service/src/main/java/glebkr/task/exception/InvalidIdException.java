package glebkr.task.exception;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("ID must be a positive integer");
    }
}
