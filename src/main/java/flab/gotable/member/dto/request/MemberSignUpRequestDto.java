package flab.gotable.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@RequiredArgsConstructor
@ToString
public class MemberSignUpRequestDto {

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{2,9}",
            message = "아이디는 영문, 숫자만 가능하며 4 ~ 12자리여야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,30}",
            message = "비밀번호는 영문과 숫자 조합으로 8 ~ 30자리여야 합니다.")
    private String password;

    @NotBlank(message = "전화번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$" , message = "전화번호 형식은 010-0000-0000입니다.")
    private String phone;

    public void setPassword(String password) {
        this.password = password;
    }
}
