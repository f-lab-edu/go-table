package flab.gotable.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public ApplicationException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
