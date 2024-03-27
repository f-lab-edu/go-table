package flab.gotable.service;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    final Member member = new Member();
    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(memberSignUpRequestDto.getPassword()));

        memberRepository.saveMember(memberSignUpRequestDto);
    }
}
