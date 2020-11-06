package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public final class RedirectHelper {
    private RedirectHelper() {
    }

    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String page) throws IOException {
        String location = req.getContextPath() + page;
        resp.sendRedirect(location);
    }

    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String page, String param, String value)
            throws IOException {
        String location = req.getContextPath() + page + '?' + param + '=' + value;
        resp.sendRedirect(location);
    }

    public static void redirectToReferer(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String referer = req.getHeader("Referer");
        if (isNull(referer) || referer.contains(Pages.LOGIN)) {
            resp.sendRedirect(req.getContextPath() + Pages.MY_WALL);
            return;
        }
        String url = req.getRequestURL().toString();
        if (referer.equals(url)) {
            referer = req.getContextPath();
        }
        resp.sendRedirect(referer);
    }
}
