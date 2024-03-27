package flab.gotable.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResponseDto {
    private HttpStatus httpStatus;
    private String message;
}
