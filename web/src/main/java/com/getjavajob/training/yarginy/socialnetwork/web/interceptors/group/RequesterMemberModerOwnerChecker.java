package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.group;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

@Component
public class RequesterMemberModerOwnerChecker extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        boolean isMemberRequester = !isNull(req.getAttribute("member")) && !isNull(req.getAttribute("requestOwner"));
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("moderator")) ||
                !isNull(req.getAttribute("owner")) || isMemberRequester) {
            return true;
        }
        res.sendRedirect(req.getContextPath() + Pages.ACCOUNT_WALL);
        return false;
    }
}
