package flab.gotable.exception;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public MemberNotFoundException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
