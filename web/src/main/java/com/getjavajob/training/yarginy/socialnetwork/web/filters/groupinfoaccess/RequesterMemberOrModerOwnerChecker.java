package com.getjavajob.training.yarginy.socialnetwork.web.filters.groupinfoaccess;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class RequesterMemberOrModerOwnerChecker extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!isNull(req.getAttribute("admin")) || !isNull(req.getAttribute("moderator")) ||
                !isNull(req.getAttribute("owner")) ||
                (!isNull(req.getAttribute("member")) && !isNull(req.getAttribute("requestOwner")))) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(req.getContextPath() + Pages.ACCOUNT_WALL);
        }
    }
}
