package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AccountUpdateAccessCheckerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("owner"))) {
            filterChain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + Pages.MY_WALL);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
