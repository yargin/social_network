package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class RedirectHelper {
    private static final String START_JSP = "/WEB-INF/jsps/myWall.jsp";

    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String location) throws IOException {
//        resp.setHeader("Location", location);
//        resp.setStatus(HttpServletResponse.SC_FOUND);
        resp.sendRedirect(location);
    }

    public static void redirectToReferer(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String referer = req.getHeader("Referer");
        if (isNull(referer) || referer.contains("/login")) {
            resp.sendRedirect(req.getContextPath() + "/mywall");
            return;
        }
        String url = req.getRequestURL().toString();
        if (referer.equals(url)) {
            referer = req.getContextPath();
        }
        redirect(req, resp, referer);
    }
}
