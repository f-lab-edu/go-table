package flab.gotable.exception;

import lombok.Getter;

@Getter
public class DuplicatedIdException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public DuplicatedIdException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
