package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static java.util.Objects.isNull;

public class AccountWallIdSetterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        if (isNull(request.getParameter(REQUESTED_ID))) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            long requestedId = (long) req.getSession().getAttribute(USER_ID);
            redirect(req, resp, Pages.MY_WALL, REQUESTED_ID, "" + requestedId);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
