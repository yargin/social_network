package com.getjavajob.training.yarginy.socialnetwork.web.servlethelpers;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.lang.System.out;
import static java.util.Objects.isNull;

public class AuthHelper {
    public static void checkAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Account account = (Account) session.getAttribute("account");
        out.println(account);
        if (isNull(account)) {
//            request.getRequestDispatcher("/WEB-INF/jsps/login.jsp").forward(request, response);
//            redirect(request, response, request.getContextPath() + "/login");
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
