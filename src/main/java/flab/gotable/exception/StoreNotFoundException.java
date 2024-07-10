package flab.gotable.exception;

public class StoreNotFoundException extends ApplicationException {
    public StoreNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}