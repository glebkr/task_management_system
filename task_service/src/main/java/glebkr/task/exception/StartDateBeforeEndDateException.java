package glebkr.task.exception;

import java.time.LocalDateTime;

public class StartDateBeforeEndDateException extends RuntimeException {
    public StartDateBeforeEndDateException(LocalDateTime startDate, LocalDateTime endDate) {
        super(STR."Start date: \{startDate} coulnd't be before end date: \{endDate}");
    }
}
