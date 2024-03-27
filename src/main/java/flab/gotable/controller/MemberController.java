package flab.gotable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    @GetMapping("/signup")
    public String signUpForm() {
        return "signup";
    }
}
