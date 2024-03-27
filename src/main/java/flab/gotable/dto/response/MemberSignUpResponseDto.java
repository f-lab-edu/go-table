package flab.gotable.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberSignUpResponseDto {
    private final String name;
    private final String id;
    private final String password;
    private final String phone;
}
