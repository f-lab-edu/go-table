package flab.gotable.controller;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.exception.DuplicatedIdException;
import flab.gotable.mapper.MemberMapper;
import flab.gotable.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

class MemberAPIControllerTest {

    private MemberAPIController memberAPIController;

    @BeforeEach
    void setup() {
        Map<String, Member> memberMap = new HashMap<>();

        memberAPIController = new MemberAPIController(
                new MemberService(new MemberMapper() {
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
                }) {
                    @Override
                    public boolean isDuplicatedId(String id) {
                        return memberMap.containsKey(id);
                    }
                });
    }

    @Test
    @DisplayName("중복된 아이디가 존재하지 않을 경우 200 OK를 반환한다.")
    void duplicatedIdNotExist() {
        // given
        memberAPIController.signup(new MemberSignUpRequestDto("제로영", "zero0", "q1w2e3r4", "010-1111-2222"));

        // when
        ResponseEntity<HttpStatus> testId = memberAPIController.isDuplicatedId("zero010101");

        // then
        Assertions.assertEquals(HttpStatus.OK, testId.getStatusCode());
    }

    @Test
    @DisplayName("중복된 아이디가 존재할 경우 DuplicatedIdException 예외를 발생시킨다.")
    void duplicatedIdExist() {
        // given
        memberAPIController.signup(new MemberSignUpRequestDto("오소영", "sozero", "q1w2e3r4", "010-1111-2222"));

        // Then
        Assertions.assertThrows(DuplicatedIdException.class, () -> memberAPIController.signup(new MemberSignUpRequestDto("오소영", "sozero", "q1w2e3r4", "010-1111-2222")));
    }
}