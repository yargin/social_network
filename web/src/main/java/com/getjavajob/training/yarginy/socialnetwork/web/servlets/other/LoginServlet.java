package com.getjavajob.training.yarginy.socialnetwork.web.servlets.other;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.web.servlets.AbstractGetPostServlet;
import com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Jsps;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_EMAIL;
import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_PASSWORD;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirectToReferer;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER;
import static com.getjavajob.training.yarginy.socialnetwork.web.staticvalues.Attributes.USER_ID;
import static java.util.Objects.isNull;

public class LoginServlet extends AbstractGetPostServlet {
    private static final String ERROR = "logerror";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void safeDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            assignSessionParameters(req, account);
            redirectToReferer(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher(Jsps.LOGIN).forward(req, resp);
        }
    }

    private void assignSessionParameters(HttpServletRequest req, Account account) {
        req.getSession().setAttribute(USER, account);
        req.getSession().setAttribute(USER_ID, account.getId());
    }

    @Override
    protected void safeDoPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            safeDoGet(req, resp);
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

        assignSessionParameters(req, account);
        redirectToReferer(req, resp);
    }
}
