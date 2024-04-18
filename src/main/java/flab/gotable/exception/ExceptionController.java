package flab.gotable.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gotable.dto.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(DuplicatedIdException.class)
    public ResponseEntity<MemberResponse> handleDuplicatedIdException(DuplicatedIdException e) {
        log.error("handleDuplicatedIdException", e);

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(MemberResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MemberResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MemberResponse.fail(new JSONObject(map).toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("handleException", e);

        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(MemberResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));

    }
}
