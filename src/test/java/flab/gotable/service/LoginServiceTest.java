package flab.gotable.service;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static flab.gotable.service.LoginService.MEMBER_ID;

class LoginServiceTest {
    private LoginService loginService;
    private HttpSession httpSession;

    @BeforeEach
    void setup() {
        loginService = new LoginService(
                httpSession = new HttpSession() {
                    private final Map<String, Object> sessionMap = new HashMap<>();
                    @Override
                    public long getCreationTime() {
                        return 0;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public long getLastAccessedTime() {
                        return 0;
                    }

                    @Override
                    public ServletContext getServletContext() {
                        return null;
                    }

                    @Override
                    public void setMaxInactiveInterval(int i) {

                    }

                    @Override
                    public int getMaxInactiveInterval() {
                        return 0;
                    }

                    @Override
                    public Object getAttribute(String s) {
                        return sessionMap.get(s);
                    }

                    @Override
                    public Enumeration<String> getAttributeNames() {
                        return null;
                    }

                    @Override
                    public void setAttribute(String s, Object o) {
                        sessionMap.put(s, o);
                    }

                    @Override
                    public void removeAttribute(String s) {
                        sessionMap.remove(s);
                    }

                    @Override
                    public void invalidate() {

                    }

                    @Override
                    public boolean isNew() {
                        return false;
                    }
                }
        );
    }

    @Test
    @DisplayName("로그인에 성공하는 경우 세션에 id를 저장한다.")
    void whenLoginSuccessThenSetSession() {
        // when
        loginService.login("testId");

        // then
        Assertions.assertNotEquals(null, httpSession.getAttribute(MEMBER_ID));
        Assertions.assertEquals("testId", httpSession.getAttribute(MEMBER_ID));
    }

    @Test
    @DisplayName("로그아웃에 성공하는 경우 세션에 저장된 id를 제거한다.")
    void whenLogoutSuccessThenRemoveSession() {
        // given
        httpSession.setAttribute(MEMBER_ID, "testId");

        // when
        loginService.logout();

        // then
        Assertions.assertNull(httpSession.getAttribute(MEMBER_ID));
    }
}
