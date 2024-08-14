package flab.gotable.exception;

public class InvalidReservationTimeException extends ApplicationException {
    public InvalidReservationTimeException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
