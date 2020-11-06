package com.getjavajob.training.yarginy.socialnetwork.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.FIRST_REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.SECOND_REQUESTED_ID;

public class TwoIdValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String stringFirstRequestedId = req.getParameter(FIRST_REQUESTED_ID);
        String stringSecondRequestedId = req.getParameter(SECOND_REQUESTED_ID);
        long firstRequestedId;
        long secondRequestedId;
        try {
            firstRequestedId = Long.parseLong(stringFirstRequestedId);
            secondRequestedId = Long.parseLong(stringSecondRequestedId);
        } catch (NumberFormatException e) {
            redirectToReferer(req, resp);
            return;
        }
        if (firstRequestedId < 1 || secondRequestedId < 1) {
            redirectToReferer(req, resp);
            return;
        }
        req.setAttribute(FIRST_REQUESTED_ID, firstRequestedId);
        req.setAttribute(SECOND_REQUESTED_ID, secondRequestedId);
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
