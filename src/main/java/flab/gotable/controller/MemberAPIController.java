package flab.gotable.controller;

import flab.gotable.dto.request.MemberSignUpRequestDto;
import flab.gotable.service.MemberService;
import flab.gotable.utils.ResponseDto;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        memberService.signUp(memberSignUpRequestDto);

        ResponseDto res = new ResponseDto(HttpStatus.OK, "SUCCESS");
        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
