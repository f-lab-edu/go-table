package flab.gotable.service;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public void signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        Member member = MemberSignUpRequestDto.toEntity(memberSignUpRequestDto,passwordEncoder);

        memberMapper.saveMember(member);
    }

    public boolean isDuplicatedId(String id) {
        return memberMapper.existId(id);
    }
}
