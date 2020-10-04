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

import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_EMAIL;
import static com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectData.WRONG_PASSWORD;
import static com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers.RedirectHelper.redirect;
import static java.util.Objects.isNull;

public class LoginServlet extends HttpServlet {
    private static final String JSP = "/WEB-INF/jsps/login.jsp";
    private static final String ERROR = "logerror";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
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

        HttpSession session = req.getSession();
        session.setAttribute("account", account);
        redirect(req, resp, req.getContextPath() + "/mywall");
    }
}
