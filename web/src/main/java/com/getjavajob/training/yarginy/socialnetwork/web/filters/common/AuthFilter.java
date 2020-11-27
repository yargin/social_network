package com.getjavajob.training.yarginy.socialnetwork.web.filters.common;

import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthFilter extends HttpFilter {
    private String[] ignoredPages;

    @Override
    public void init(FilterConfig filterConfig) {
        String ignoredParam = filterConfig.getInitParameter("pathToIgnore");
        ignoredPages = ignoredParam.split(":");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();
        for (String ignored : ignoredPages) {
            if (path.contains(ignored)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        Object userIdObject = session.getAttribute(Attributes.USER_ID);
        if (isNull(userIdObject)) {
            RedirectHelper.redirect(req, resp, Pages.LOGIN);
            return;
        }
        long userId = (long) userIdObject;

        if (userId < 1) {
            RedirectHelper.redirect(req, resp, Pages.LOGIN);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
