package flab.gotable.service;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.request.MemberLoginRequestDto;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.MemberNotFoundException;
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
        memberMapper.saveMember(member);
    }

    @Transactional(readOnly = true)
    public boolean isDuplicatedId(String id) {
        return memberMapper.existId(id);
    }

    @Transactional
    public boolean findMemberByIdAndPassword(MemberLoginRequestDto memberLoginRequestDto) {
        Member member = findMemberById(memberLoginRequestDto.getId());

        if(passwordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Member findMemberById(String id) {
        if(!memberMapper.existId(id)) {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND_ID, ErrorCode.MEMBER_NOT_FOUND_ID.getMessage());
        }

        return memberMapper.findMemberById(id);
    }
}
