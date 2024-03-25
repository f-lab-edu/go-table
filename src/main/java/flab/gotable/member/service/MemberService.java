package flab.gotable.member.service;

import flab.gotable.member.dto.request.MemberSignUpRequestDto;
import flab.gotable.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public void signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        memberSignUpRequestDto.setPassword(passwordEncoder.encode(memberSignUpRequestDto.getPassword()));
        memberMapper.saveMember(memberSignUpRequestDto);
    }
}
