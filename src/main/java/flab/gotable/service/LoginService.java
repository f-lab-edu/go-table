package flab.gotable.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    public static final String MEMBER_ID = "MEMBER_ID";

    @Transactional
    public void login(String id, HttpSession httpSession) {
        httpSession.setAttribute(MEMBER_ID, id);
    }

    @Transactional
    public void logout(HttpSession httpSession) {
        httpSession.removeAttribute(MEMBER_ID);
    }
}
