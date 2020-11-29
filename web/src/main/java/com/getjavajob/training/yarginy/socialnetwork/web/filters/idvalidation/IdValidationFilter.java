package com.getjavajob.training.yarginy.socialnetwork.web.filters.idvalidation;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;

public class IdValidationFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException,
            ServletException {
        String stringRequestedId = req.getParameter(REQUESTED_ID);
        long requestedId;
        try {
            requestedId = Long.parseLong(stringRequestedId);
        } catch (NumberFormatException e) {
            redirectToReferer(req, resp);
            return;
        }
        if (requestedId < 1) {
            redirectToReferer(req, resp);
            return;
        }
        req.setAttribute(REQUESTED_ID, requestedId);
        filterChain.doFilter(req, resp);
    }
}
