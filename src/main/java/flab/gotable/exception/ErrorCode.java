package flab.gotable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_ID(HttpStatus.CONFLICT, "중복된 id 입니다."),
    MEMBER_NOT_FOUND_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 id입니다."),
    MEMBER_NOT_FOUND_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 시스템 에러입니다.");

    private HttpStatus httpStatus;
    private String message;
}
