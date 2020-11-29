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

public class SessionIdSetterFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException,
            ServletException {
        String uri = req.getRequestURI();
        String page = "";
        if (uri.contains("wall")) {
            page = Pages.WALL;
        } else if (uri.contains("friends")) {
            page = Pages.FRIENDS;
        } else if (uri.contains("dialogs")) {
            page = Pages.DIALOGS;
        } else if (uri.contains("groups")) {
            page = Pages.GROUPS;
        }
        if (isNull(req.getParameter(REQUESTED_ID)) && !page.isEmpty()) {
            long requestedId = (long) req.getSession().getAttribute(USER_ID);
            redirect(req, resp, page, REQUESTED_ID, "" + requestedId);
            return;
        }
        filterChain.doFilter(req, resp);
    }
}
