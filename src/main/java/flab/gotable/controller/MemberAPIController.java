package flab.gotable.controller;

import flab.gotable.domain.entity.Member;
import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.request.MemberLoginRequestDto;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.exception.DuplicatedIdException;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.MemberNotFoundException;
import flab.gotable.service.LoginService;
import flab.gotable.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberAPIController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        if (memberService.isDuplicatedId(memberSignUpRequestDto.getId())) {
            throw new DuplicatedIdException(ErrorCode.DUPLICATED_ID, ErrorCode.DUPLICATED_ID.getMessage());
        }

        memberService.signUp(memberSignUpRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(memberSignUpRequestDto, "회원가입 성공"));
    }

    @GetMapping("/duplicated/{id}")
    public ResponseEntity<HttpStatus> isDuplicatedId(@PathVariable("id") String id) {
        if (memberService.isDuplicatedId(id)) {
            throw new DuplicatedIdException(ErrorCode.DUPLICATED_ID, ErrorCode.DUPLICATED_ID.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        if(!memberService.findMemberByIdAndPassword(memberLoginRequestDto)) {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND_PASSWORD, ErrorCode.MEMBER_NOT_FOUND_PASSWORD.getMessage());
        }

        loginService.login(memberLoginRequestDto.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(memberLoginRequestDto, "로그인 성공"));
    }
}
