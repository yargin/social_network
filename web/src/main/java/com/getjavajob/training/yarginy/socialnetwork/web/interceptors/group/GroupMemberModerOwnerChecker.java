package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

@Component
public class GroupMemberModerOwnerChecker extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("owner")) ||
                !isNull(req.getAttribute("member")) || !isNull(req.getAttribute("moderator"))) {
            return true;
        }
        res.sendRedirect(req.getContextPath() + Pages.ACCOUNT_WALL);
        return false;
    }
}