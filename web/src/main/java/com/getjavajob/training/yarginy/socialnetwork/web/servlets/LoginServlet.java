package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_EMAIL;
import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_PASSWORD;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static java.util.Objects.isNull;

public class LoginServlet extends HttpServlet {
    private static final String JSP = "/WEB-INF/jsps/login.jsp";
    private static final String ERROR = "logerror";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "userName";
    private final AuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = null;
        String password = null;
        Cookie[] cookies = req.getCookies();

        if (!isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (EMAIL.equals(cookie.getName())) {
                    email = cookie.getValue();
                } else if (PASSWORD.equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }
        try {
            Account account = authService.login(email, password);
            req.getSession().setAttribute(USER_NAME, account.getName());
            redirectToReferer(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher(JSP).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account;
        String email = null;
        String password;
        try {
            email = req.getParameter(EMAIL);
            password = req.getParameter(PASSWORD);
            account = authService.login(email, password);
        } catch (IncorrectDataException e) {
            switch (e.getType()) {
                case NOT_AN_EMAIL:
                case WRONG_EMAIL:
                    req.setAttribute(ERROR, WRONG_EMAIL.getPropertyKey());
                    break;
                case NOT_A_PASSWORD:
                case WRONG_PASSWORD:
                case PASSWORD_TOO_LONG:
                case PASSWORD_TOO_SHORT:
                    req.setAttribute(ERROR, WRONG_PASSWORD.getPropertyKey());
                    if (!isNull(email)) {
                        req.setAttribute(EMAIL, email);
                    }
                    break;
                default:
                    break;
            }
            doGet(req, resp);
            return;
        }

        String rememberMe = req.getParameter("rememberMe");
        if ("on".equals(rememberMe)) {
            Cookie emailCookie = new Cookie(EMAIL, email);
            emailCookie.setMaxAge(2);
            Cookie passwordCookie = new Cookie(PASSWORD, email);
            passwordCookie.setMaxAge(2);
            resp.addCookie(emailCookie);
            resp.addCookie(passwordCookie);
        }

        HttpSession session = req.getSession();
        session.setAttribute(USER_NAME, account.getName());
        redirectToReferer(req, resp);
    }
}
