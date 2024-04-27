package flab.gotable.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 정상 및 에러에 대한 응답을 동일한 공통 포맷을 사용하여 처리하는 것을 목적으로 생성한 클래스
 */
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final String status;
    private final T data;
    private final String message;

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>("success", data, message);
    }

    public static ApiResponse<Void> fail(String message) {
        return new ApiResponse<Void>("fail", null, message);
    }
}
