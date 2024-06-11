package flab.gotable.exception;


public class UnAuthenticatedException extends ApplicationException {

    public UnAuthenticatedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
