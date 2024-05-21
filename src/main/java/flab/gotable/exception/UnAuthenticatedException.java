package flab.gotable.exception;

import lombok.Getter;

@Getter
public class UnAuthenticatedException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public UnAuthenticatedException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
