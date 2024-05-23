package flab.gotable.dto.request;

import flab.gotable.domain.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MemberLoginRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,12}$", message = "아이디는 특수문자 입력이 불가능하며 최대 12자리까지 입력 가능합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Size(max=30, message = "비밀번호는 최대 30자리까지 입력 가능합니다.")
    private String password;

    public MemberLoginRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
