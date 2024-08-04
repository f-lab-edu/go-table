package flab.gotable.exception;

public class LockFailureException extends ApplicationException  {
    public LockFailureException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
