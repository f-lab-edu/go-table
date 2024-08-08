package flab.gotable.exception;

public class InvalidReservationException extends ApplicationException {
    public InvalidReservationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
