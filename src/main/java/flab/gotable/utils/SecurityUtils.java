package flab.gotable.utils;

import jakarta.servlet.http.HttpServletRequest;

import static flab.gotable.service.LoginService.MEMBER_ID;

public class SecurityUtils {
    public static boolean hasAuthentication(HttpServletRequest request) {
        if(request.getSession().getAttribute(MEMBER_ID) == null) {
            return false;
        }
        return true;
    }
}
