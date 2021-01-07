package com.getjavajob.training.yarginy.socialnetwork.web.servlets.other;

import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Pages;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public class LogoutServlet extends AbstractGetServlet {
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "userName";

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();

        Cookie[] cookies = req.getCookies();

        if (!isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (USER_NAME.equals(cookie.getName()) || PASSWORD.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        redirect(req, resp, Pages.LOGIN);
    }
}
