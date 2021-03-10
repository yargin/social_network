package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.common;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharacterSetSetter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        return true;
    }
}
