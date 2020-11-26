package com.getjavajob.training.yarginy.socialnetwork.web.filters.accountinfoaccess;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class FriendCheckerFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("friend"))) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(req.getContextPath() + Pages.WALL);
        }
    }
}
