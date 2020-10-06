package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthFilter implements Filter {
    private String ignored;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignored = filterConfig.getInitParameter("pathToIgnore");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        Account account = (Account) session.getAttribute("account");
        System.out.println(this.getClass().getName());
        System.out.println(account);
        if (isNull(account)) {
            String path = httpRequest.getRequestURI();
            System.out.println("path: " + path);
            System.out.println("ignored: " + ignored);
            System.out.println(this.getClass().getCanonicalName());
            //todo: read ignored paths from somewhere
            if (path.contains(ignored)) {
                filterChain.doFilter(httpRequest, response);
            } else {
                httpRequest.getRequestDispatcher("/WEB-INF/jsps/login.jsp").forward(httpRequest, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
