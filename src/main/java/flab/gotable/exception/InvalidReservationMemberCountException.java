package flab.gotable.exception;

public class InvalidReservationMemberCountException extends ApplicationException {
    public InvalidReservationMemberCountException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
