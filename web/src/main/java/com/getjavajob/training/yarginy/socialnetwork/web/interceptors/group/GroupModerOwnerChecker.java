package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

@Component
public class GroupModerOwnerChecker extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("owner")) || !isNull(req.
                getAttribute("moderator"))) {
            return true;
        }
        resp.sendRedirect(req.getContextPath() + Pages.WALL);
        return false;
    }
}
