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
    MEMBER_UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 식당 id입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 시스템 에러입니다."),
    DUPLICATED_RESERVATION_TIME(HttpStatus.BAD_REQUEST, "해당 시간을 포함한 예약 내역이 존재합니다."),
    RESERVATION_TIME_NOT_FOUND(HttpStatus.BAD_REQUEST, "예약하고자 하는 시간이 일반/특수 영업 스케줄에 존재하지 않습니다.");
    private HttpStatus httpStatus;
    private String message;
}
