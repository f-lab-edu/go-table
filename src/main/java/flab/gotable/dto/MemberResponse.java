package flab.gotable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 정상 및 에러에 대한 응답을 동일한 공통 포맷을 사용하여 처리하는 것을 목적으로 생성한 클래스
 */
@Getter
@AllArgsConstructor
public class MemberResponse<T> {
    private String status;
    private T data;
    private String message;

    public static <T> MemberResponse<T> ok(T data, String message) {
        return new MemberResponse<>("success", data, message);
    }

    public static MemberResponse<Void> fail(String message) {
        return new MemberResponse<Void>("fail", null, message);
    }
}
