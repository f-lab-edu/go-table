package flab.gotable.Interceptor;

import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.UnAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static flab.gotable.service.LoginService.MEMBER_ID;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getSession().getAttribute(MEMBER_ID) == null) {
            throw new UnAuthenticatedException(ErrorCode.MEMBER_UNAUTHENTICATED, ErrorCode.MEMBER_UNAUTHENTICATED.getMessage());
        }

        return true;
    }
}
