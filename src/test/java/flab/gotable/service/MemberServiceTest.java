package flab.gotable.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import flab.gotable.domain.entity.Member;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    void setup() {
        memberService = new MemberService(new MemberMapper() {
            private Map<String, Member> memberMap = new HashMap<>();

            @Override
            public void saveMember(Member member) {
                memberMap.put(member.getId(), member);
            }

            @Override
            public boolean existId(String id) {
                return memberMap.containsKey(id);
            }
        }, new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return null;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return false;
            }
        });
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
}