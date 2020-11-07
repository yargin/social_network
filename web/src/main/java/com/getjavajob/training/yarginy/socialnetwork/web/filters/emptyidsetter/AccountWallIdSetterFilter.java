package com.getjavajob.training.yarginy.socialnetwork.web.filters.emptyidsetter;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.REQUESTED_ID;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static java.util.Objects.isNull;

public class AccountWallIdSetterFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException,
            ServletException {
        if (isNull(req.getParameter(REQUESTED_ID))) {
            long requestedId = (long) req.getSession().getAttribute(USER_ID);
            redirect(req, resp, Pages.MY_WALL, REQUESTED_ID, "" + requestedId);
            return;
        }
        filterChain.doFilter(req, resp);
    }
}
