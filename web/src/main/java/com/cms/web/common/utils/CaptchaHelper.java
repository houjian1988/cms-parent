package com.cms.web.common.utils;

import javax.servlet.http.HttpSession;

/**
 * 验证输入的验证码是否正确
 * User: houjian
 * Date: 16-03-03
 */
public class CaptchaHelper {

    //HttpSession中保存的验证码名称
    public static final String CAPTCHA = "authCode";

    public static boolean check(String code, HttpSession session) {
        if (code.equalsIgnoreCase(getCaptcha(session))) {
            session.removeAttribute(CAPTCHA);
            return true;
        } else {
            return false;
        }
    }

    public static String getCaptcha(HttpSession session) {
        return (String) session.getAttribute(CAPTCHA);
    }

}
