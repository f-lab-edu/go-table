package flab.gotable.dto.request;

import flab.gotable.domain.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter
@ToString
public class MemberSignUpRequestDto {

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{4,12}",
            message = "아이디는 영문, 숫자만 가능하며 4 ~ 12자리여야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,30}",
            message = "비밀번호는 영문과 숫자 조합으로 8 ~ 30자리여야 합니다.")
    private String password;

    @NotBlank(message = "전화번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{4})-\\d{4}$" , message = "전화번호 형식은 010-0000-0000입니다.")
    private String phone;

    public MemberSignUpRequestDto(String name, String id, String password, String phone) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.phone = phone;
    }

    public static Member toEntity(MemberSignUpRequestDto memberSignUpRequestDto, String password) {
        Member member = new Member();

        member.setName(memberSignUpRequestDto.getName());
        member.setId(memberSignUpRequestDto.getId());
        member.setPassword(password);
        member.setPhone(memberSignUpRequestDto.getPhone());

        return member;
    }
}
