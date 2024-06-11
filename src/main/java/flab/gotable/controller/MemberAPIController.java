package flab.gotable.controller;

import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.request.MemberLoginRequestDto;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.exception.DuplicatedIdException;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.MemberNotFoundException;
import flab.gotable.service.LoginService;
import flab.gotable.service.MemberService;
import jakarta.servlet.http.HttpSession;
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

    /*
    * [Spring Security를 사용하지 않은 이유]
    * 로그인/로그아웃을 개발하며 인증 기능을 구현하는데 집중했고, 해당 기능을 구현하기 위해 Spring Security를 사용하는 것은 오버엔지니어링인 것 같다고 판단했습니다.
    * 다른 보안적인 요소를 추가하게 된다면 추후 Spring Security를 도입해도 될 것 같다고 생각하여 Spring Security를 사용하지 않았습니다.
    * */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto, HttpSession httpSession) {
        if(!memberService.isvalidMember(memberLoginRequestDto)) {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND_PASSWORD, ErrorCode.MEMBER_NOT_FOUND_PASSWORD.getMessage());
        }

        loginService.login(memberLoginRequestDto.getId(), httpSession);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(memberLoginRequestDto, "로그인 성공"));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession httpSession) {
        loginService.logout(httpSession);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "로그아웃 성공"));
    }
}
