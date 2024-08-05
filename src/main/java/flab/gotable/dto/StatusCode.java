package flab.gotable.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    SUCCESS("success"),
    FAILED("fail");

    private final String value;
}
