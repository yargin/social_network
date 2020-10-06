package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import javax.servlet.http.*;
import java.io.IOException;

import static java.util.Objects.isNull;

public class LogoutServlet extends HttpServlet {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "userName";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(USER_NAME);

        Cookie[] cookies = req.getCookies();

        if (!isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (EMAIL.equals(cookie.getName()) || PASSWORD.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
