package flab.gotable.exception;

import flab.gotable.dto.MemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicatedIdException.class)
    public ResponseEntity<MemberResponse> handleDuplicatedIdException(DuplicatedIdException e) {

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(MemberResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MemberResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MemberResponse.fail(e.getMessage()));
    }
}
