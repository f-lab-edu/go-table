package flab.gotable.Interceptor;

import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.UnAuthenticatedException;
import flab.gotable.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
* [Filter / Interceptor 중 Interceptor를 선택하고 구현한 이유]
* Filter는 Spring 범위 밖에서 모든 서블릿 요청을 받아 처리하기 때문에 Spring에 의해 필터링되는 요청들을 전부 받습니다.
* 반면 Interceptor는 Spring 영역에서 동작하기 때문에 성능적인 면에서 이점이 있을거라 판단했습니다.
* 또한, Spring 공식문서(https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html)에 따르면
* 기본 가이드라인으로 인터셉터는 핸들러의 반복 코드 제거 및 권한 확인 시 사용하는 것이 적합하고, 필터는 multipart form, GZIP 압축과 같은 요청 컨텐츠 및 보기 컨텐츠 처리에 적합하다 설명하여 Interceptor를 선택했습니다.
* */
@RequiredArgsConstructor
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(SecurityUtils.hasAuthentication(request) == false) {
            throw new UnAuthenticatedException(ErrorCode.MEMBER_UNAUTHENTICATED, ErrorCode.MEMBER_UNAUTHENTICATED.getMessage());
        }

        return true;
    }
}
