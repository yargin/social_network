package com.getjavajob.training.yarginy.socialnetwork.web.filters.idvalidation;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.RECEIVER_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTER_ID;

public class TwoIdValidationFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException,
            ServletException {
        String stringFirstRequestedId = req.getParameter(REQUESTER_ID);
        String stringSecondRequestedId = req.getParameter(RECEIVER_ID);
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
        req.setAttribute(REQUESTER_ID, firstRequestedId);
        req.setAttribute(RECEIVER_ID, secondRequestedId);
        filterChain.doFilter(req, resp);
    }
}
