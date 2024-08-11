package flab.gotable.service;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.request.MemberLoginRequestDto;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.exception.MemberNotFoundException;
import flab.gotable.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    void setup() {
        memberService = new MemberService(new MemberMapper() {
            private final Map<String, Member> memberMap = new HashMap<>();

            @Override
            public void saveMember(Member member) {
                memberMap.put(member.getId(), member);
            }

            @Override
            public boolean existId(String id) {
                return memberMap.containsKey(id);
            }

            @Override
            public Member findMemberById(String id) {
                return memberMap.get(id);
            }

            @Override
            public boolean isMemberExistSeq(long seq) {
                return memberMap.containsKey(seq);
            }

        }, new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if(!rawPassword.equals(encodedPassword)) {
                    return false;
                }
                return true;
            }
        }) {
            @Override
            public void signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
                super.signUp(memberSignUpRequestDto);
            }
        };
    }

    @Test
    @DisplayName("중복된 id가 존재하는 경우 TRUE를 반환한다.")
    void DuplicatedIdExist() {
        // given
        memberService.signUp(new MemberSignUpRequestDto("제로영", "sozero", "q1w2e3r4", "010-1234-5678"));

        // when
        boolean isDuplicatedId = memberService.isDuplicatedId("sozero");

        // then
        Assertions.assertTrue(isDuplicatedId);
    }

    @Test
    @DisplayName("중복된 id가 존재하지 않는 경우 FALSE를 반환한다.")
    void DuplicatedIdNotExist() {
        // given
        memberService.signUp(new MemberSignUpRequestDto("제로영", "sozero", "q1w2e3r4", "010-1234-5678"));

        // when
        boolean isDuplicatedId = memberService.isDuplicatedId("testId");

        // then
        Assertions.assertFalse(isDuplicatedId);
    }

    @Test
    @DisplayName("해당 id로 가입한 회원이 존재하는 경우 가입한 Member를 반환한다.")
    void isExistId() {
        // given
        memberService.signUp(new MemberSignUpRequestDto("제로영", "sozero", "q1w2e3r4", "010-1234-5678"));

        // when
        Member member = memberService.findMemberById("sozero");

        // then
        Assertions.assertEquals("제로영", member.getName());
        Assertions.assertEquals("sozero", member.getId());
        Assertions.assertEquals("q1w2e3r4", member.getPassword());
        Assertions.assertEquals("010-1234-5678", member.getPhone());
    }

    @Test
    @DisplayName("해당 id로 가입한 회원이 존재하지 않는 경우 MemberNotFoundException 예외를 발생시킨다.")
    void isNotExistId() {
        // given
        memberService.signUp(new MemberSignUpRequestDto("제로영", "sozero", "q1w2e3r4", "010-1234-5678"));

        // then
        Assertions.assertThrows(MemberNotFoundException.class, () -> { memberService.findMemberById("testId"); });
    }

    @Test
    @DisplayName("해당 id로 가입한 회원이 존재하고 비밀번호가 일치하는 경우 TRUE를 반환한다.")
    void isExistMember() {
        // given
        memberService.signUp(new MemberSignUpRequestDto("제로영", "sozero", "q1w2e3r4", "010-1234-5678"));

        // when
        boolean isExistMember = memberService.isValidMember(new MemberLoginRequestDto("sozero", "q1w2e3r4"));

        // then
        Assertions.assertTrue(isExistMember);
    }

    @Test
    @DisplayName("해당 id로 가입한 회원이 존재하고 비밀번호가 일치하지 않는 경우 FALSE를 반환한다.")
    void isNotExistMember() {
        // given
        memberService.signUp(new MemberSignUpRequestDto("제로영", "sozero", "q1w2e3r4", "010-1234-5678"));

        // when
        boolean isExistMember = memberService.isValidMember(new MemberLoginRequestDto("sozero", "r5t6y7u8"));

        // then
        Assertions.assertFalse(isExistMember);
    }
}