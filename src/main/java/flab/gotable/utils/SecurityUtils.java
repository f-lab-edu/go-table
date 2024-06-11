package flab.gotable.utils;

import jakarta.servlet.http.HttpServletRequest;

import static flab.gotable.service.LoginService.MEMBER_ID;

public class SecurityUtils {
    public static String hasAuthentication(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(MEMBER_ID);
    }
}
