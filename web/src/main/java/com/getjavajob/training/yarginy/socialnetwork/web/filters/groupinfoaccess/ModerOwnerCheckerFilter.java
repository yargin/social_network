package com.getjavajob.training.yarginy.socialnetwork.web.filters.groupinfoaccess;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class ModerOwnerCheckerFilter extends HttpFilter {
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("owner")) || !isNull(req.
                getAttribute("moderator"))) {
            chain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + Pages.WALL);
        }
    }
}
