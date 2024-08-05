package flab.gotable.exception;

public class ScheduleNotFoundException extends ApplicationException {
    public ScheduleNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
