package flab.gotable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gotable.exception.MemberNotFoundException;
import flab.gotable.exception.UnAuthenticatedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static flab.gotable.service.LoginService.MEMBER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class LoginServiceTest {

    private MockHttpSession session;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("로그인에 성공하는 경우 세션에 id를 저장한다.")
    void whenLoginSuccessThenSetSession() throws Exception {
        Map<String, String> requestMap = new HashMap<>();

        requestMap.put("id", "testId");
        requestMap.put("password", "1q2w3e4r5");

        String content = new ObjectMapper().writeValueAsString(requestMap);

        mockMvc.perform(
                post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .session(session)
        );

        Assertions.assertEquals("testId", session.getAttribute(MEMBER_ID));
    }

    @Test
    @DisplayName("가입한 회원 정보를 찾지 못해 로그인에 실패할 경우 MemberNotFoundException 예외를 발생시킨다.")
    void loginFailNotExistId() throws Exception {
        Map<String, String> requestMap = new HashMap<>();

        requestMap.put("id", "testId1");
        requestMap.put("password", "1q2w3e4r5");

        mockMvc.perform(
                post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestMap))
                        .session(session)
                )
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MemberNotFoundException);
                });
    }

    @Test
    @DisplayName("가입한 회원의 패스워드가 일치하지 않아 로그인에 실패할 경우 MemberNotFoundException 예외를 발생시킨다.")
    void loginFailNotExistMember() throws Exception {
        Map<String, String> requestMap = new HashMap<>();

        requestMap.put("id", "testId");
        requestMap.put("password", "1q2w3e444");

        mockMvc.perform(
                post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestMap))
                        .session(session)
                )
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MemberNotFoundException);
                });
    }

    @Test
    @DisplayName("로그아웃에 성공하는 경우 세션에 저장된 id를 제거한다.")
    void whenLogoutSuccessThenRemoveSession() throws Exception {
        session.setAttribute(MEMBER_ID, "testId");

        mockMvc.perform(
                get("/members/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
        );

        Assertions.assertNull(session.getAttribute(MEMBER_ID));
    }

    @Test
    @DisplayName("세션에 저장된 id가 없어 로그아웃에 실패하는 경우 UnAuthenticatedException 예외를 발생시킨다.")
    void logoutFail() throws Exception {
        session.removeAttribute(MEMBER_ID);

        mockMvc.perform(
                get("/members/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
        )
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof UnAuthenticatedException);
                });
    }
}
