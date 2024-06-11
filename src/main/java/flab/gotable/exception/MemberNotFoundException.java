package flab.gotable.exception;


public class MemberNotFoundException extends ApplicationException {

    public MemberNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
