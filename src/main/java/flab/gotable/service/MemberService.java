package flab.gotable.service;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        Member member = memberSignUpRequestDto.toEntity(memberSignUpRequestDto, passwordEncoder.encode(memberSignUpRequestDto.getPassword()));
        member.setPassword(member.getPassword());

        memberMapper.saveMember(member);
    }

    @Transactional(readOnly = true)
    public boolean isDuplicatedId(String id) {
        return memberMapper.existId(id);
    }
}
