package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthFilter implements Filter {
    private String[] ignoredPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ignoredParam = filterConfig.getInitParameter("pathToIgnore");
        ignoredPages = ignoredParam.split(":");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        HttpServletResponse resp = (HttpServletResponse) response;

        String userName = (String) session.getAttribute("userName");

        String path = req.getRequestURI();
        for (String ignored : ignoredPages) {
            if (path.contains(ignored)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (isNull(userName) || userName.isEmpty()) {
            RedirectHelper.redirect(req, resp, req.getContextPath() + "/login");
            return;
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
