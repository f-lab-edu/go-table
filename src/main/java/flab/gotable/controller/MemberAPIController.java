package flab.gotable.controller;

import flab.gotable.dto.request.MemberSignUpRequestDto;
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
    public ResponseEntity<HttpStatus> signup(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        boolean isDuplicated = memberService.isDuplicatedId(memberSignUpRequestDto.getId());

        if(isDuplicated) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        memberService.signUp(memberSignUpRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/duplicated/{id}")
    public ResponseEntity<HttpStatus> isDuplicatedEmail(@PathVariable("id") String id) {
        boolean isDuplicated = memberService.isDuplicatedId(id);

        if (isDuplicated) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
