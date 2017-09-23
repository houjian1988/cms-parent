package com.cms.web.common.utils;

import com.hrhnyy.dto.user.UserDto;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserHelper {

    private final static String USER_SESSION_KEY = "userDetail";

    public static void put(UserDto userDto, HttpSession session) {
        session.setAttribute(USER_SESSION_KEY, userDto);
    }

    public static void remove(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    public static void removeAndPut(UserDto userDto, HttpSession session) {
        remove(session);
        put(userDto, session);
    }

    public static UserDto user(HttpSession session) {
        Object user = session.getAttribute(USER_SESSION_KEY);
        return (UserDto) user;
    }

    public static boolean isLogin(HttpSession session) {
        return (user(session) == null) ? false : true;
    }

    public static boolean isLogOut(HttpSession session) {
        return !isLogin(session);
    }
}
