package com.getjavajob.training.yarginy.socialnetwork.web.helpers;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages.*;
import static java.util.Objects.isNull;

@Component
public class RedirectHelper {
    private static final String REFERER = "Referer";

    public void redirect(HttpServletRequest req, HttpServletResponse resp, String page) throws IOException {
        String location = req.getContextPath() + page;
        resp.sendRedirect(location);
    }

    public void redirectToReferer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader(REFERER);
        if (isNull(referer) || referer.contains(LOGIN)) {
            resp.sendRedirect(req.getContextPath() + ACCOUNT_WALL);
            return;
        }
        String url = req.getRequestURL().toString();
        if (referer.equals(url)) {
            referer = req.getContextPath();
        }
        resp.sendRedirect(referer);
    }

    public String redirectBackView(HttpServletRequest req) {
        String referer = req.getHeader(REFERER);
        if (isNull(referer) || referer.contains(LOGIN)) {
            return REDIRECT + ACCOUNT_WALL;
        }
        String url = req.getRequestURL().toString();
        if (referer.equals(url)) {
            referer = req.getContextPath();
        }
        return REDIRECT + referer;
    }
}
