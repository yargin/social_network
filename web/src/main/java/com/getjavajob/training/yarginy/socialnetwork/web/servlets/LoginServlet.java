package com.getjavajob.training.yarginy.socialnetwork.web.servlets;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthService;
import com.getjavajob.training.yarginy.socialnetwork.service.AuthServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public class LoginServlet extends HttpServlet {
    private static final String JSP = "/WEB-INF/jsps/login.jsp";
    private static final String ERROR = "error";
    private static final String EMAIL = "email";
    private final AuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account;
        String email = null;
        String password;
        try {
            email = req.getParameter(EMAIL);
            password = req.getParameter("password");
            account = authService.login(email, password);
        } catch (IncorrectDataException e) {
            switch (e.getType()) {
                case NOT_AN_EMAIL:
                    req.setAttribute(ERROR, "error.notEmail");
                    break;
                case NOT_A_PASSWORD:
                    req.setAttribute(ERROR, "error.notPassword");
                    if (!isNull(email)) {
                        req.setAttribute(EMAIL, email);
                    }
                    break;
                case WRONG_EMAIL:
                    req.setAttribute(ERROR, "error.wrongEmail");
                    break;
                case WRONG_PASSWORD:
                    req.setAttribute(ERROR, "error.wrongPassword");
                    if (!isNull(email)) {
                        req.setAttribute(EMAIL, email);
                    }
                    break;
                case PASSWORD_TOO_LONG:
                    req.setAttribute(ERROR, "error.passwordTooLong");
                    if (!isNull(email)) {
                        req.setAttribute(EMAIL, email);
                    }
                    break;
                case PASSWORD_TOO_SHORT:
                    req.setAttribute(ERROR, "error.passwordTooShort");
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

        HttpSession session = req.getSession();
        session.setAttribute("account", account);
        redirect(req, resp, req.getContextPath() + "/mywall");
    }
}
