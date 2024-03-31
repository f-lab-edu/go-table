package flab.gotable.controller;

import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberAPIController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        memberService.signUp(memberSignUpRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
