package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.LOGIN;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.WALL;
import static java.util.Objects.isNull;

public final class RedirectHelper {
    private static final String REFERER = "Referer";
    private static final String REDIRECT = "redirect:";

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

    public static void redirectToReferer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader(REFERER);
        if (isNull(referer) || referer.contains(LOGIN)) {
            resp.sendRedirect(req.getContextPath() + WALL);
            return;
        }
        String url = req.getRequestURL().toString();
        if (referer.equals(url)) {
            referer = req.getContextPath();
        }
        resp.sendRedirect(referer);
    }

    public static String redirectBackView(HttpServletRequest req) {
        String referer = req.getHeader(REFERER);
        if (isNull(referer) || referer.contains(LOGIN)) {
            return REDIRECT + WALL;
        }
        String url = req.getRequestURL().toString();
        if (referer.equals(url)) {
            referer = req.getContextPath();
        }
        return REDIRECT + referer;
    }
}
