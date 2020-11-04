package com.zhongmingyuan.fleamarket.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCoedActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCoedActual == null || !verifyCoedActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
