package flab.gotable.controller;

import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.exception.DuplicatedIdException;
import flab.gotable.exception.ErrorCode;
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
    public ResponseEntity<HttpStatus> isDuplicatedEmail(@PathVariable("id") String id) {
        if (memberService.isDuplicatedId(id)) {
            throw new DuplicatedIdException(ErrorCode.DUPLICATED_ID, ErrorCode.DUPLICATED_ID.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
