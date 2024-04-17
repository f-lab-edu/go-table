package flab.gotable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_ID(HttpStatus.CONFLICT, "중복된 id 입니다.");

    private HttpStatus httpStatus;
    private String message;
}
