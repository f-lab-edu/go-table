package flab.gotable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 시스템 에러입니다."),
    DUPLICATED_ID(HttpStatus.CONFLICT, "중복된 회원 id 입니다."),
    MEMBER_NOT_FOUND_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 회원 id입니다."),
    MEMBER_NOT_FOUND_SEQ(HttpStatus.BAD_REQUEST, "존재하지 않는 회원 seq입니다."),
    MEMBER_NOT_FOUND_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    MEMBER_UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 식당 id입니다."),
    DUPLICATED_RESERVATION_TIME(HttpStatus.BAD_REQUEST, "해당 시간을 포함한 예약 내역이 존재합니다."),
    RESERVATION_TIME_NOT_FOUND(HttpStatus.BAD_REQUEST, "예약하고자 하는 시간이 일반/특수 영업 스케줄에 존재하지 않습니다."),
    LOCK_ACQUISITION_FAILED(HttpStatus.CONFLICT, "락 획득에 실패했습니다."),
    INVALID_RESERVATION_TIME(HttpStatus.BAD_REQUEST, "예약 종료 시간이 예약 시작 시간보다 앞서거나 동일할 수 없습니다."),
    PAST_RESERVATION_TIME(HttpStatus.BAD_REQUEST, "예약 시간이 현재 시간보다 이전일 수 없습니다."),
    EXCEEDS_MAX_MEMBER_COUNT(HttpStatus.BAD_REQUEST, "예약 가능 최대 인원 수를 초과했습니다."),
    INVALID_MAX_MEMBER_COUNT(HttpStatus.BAD_REQUEST, "최대 예약 가능 인원 수는 0보다 커야 합니다.");

    private HttpStatus httpStatus;
    private String message;
}
