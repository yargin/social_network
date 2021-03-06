package com.getjavajob.training.yarginy.socialnetwork.web.interceptors.account;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

public class AccountOwnerChecker extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("owner")) ||
                !isNull(req.getAttribute("friend"))) {
            return true;
        } else {
            res.sendRedirect(req.getContextPath() + Pages.ACCOUNT_WALL);
        }
        return false;
    }
}
