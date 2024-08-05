package flab.gotable.exception;

public class DuplicatedReservationException extends ApplicationException {
    public DuplicatedReservationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
