package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.util.Objects.isNull;

public class UrlIdFilter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getServletPath();
        if (isNull(path) || path.contains(Pages.LOGIN) || path.contains(Pages.LOGOUT) || path.contains(Pages.REGISTER)) {
            doFilter(servletRequest, servletResponse, filterChain);
        }
        String params = req.getParameter(Attributes.USER_ID);
    }
}
