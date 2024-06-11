package flab.gotable.exception;


public class DuplicatedIdException extends ApplicationException {

    public DuplicatedIdException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
