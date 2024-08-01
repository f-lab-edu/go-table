package flab.gotable.exception;

public class ReservationFailedException extends ApplicationException  {
    public ReservationFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
